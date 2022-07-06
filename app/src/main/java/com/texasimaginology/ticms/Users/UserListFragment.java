package com.texasimaginology.ticms.Users;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.texasimaginology.ticms.Error.ErrorMessageDto;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListFragment extends Fragment implements SearchView.OnQueryTextListener, UserDtoAdapter.ClickListener, View.OnClickListener {
    private UserDtoAdapter userDtoAdapter;
    List<UserListDto.Content> userListDto;
    private SearchView searchView;
    private ListView recyclerViewDetail;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        defineView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.search);
        search.setVisible(true);

        searchView = (SearchView) search.getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }
/*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.activity_menu_item:

                // Not implemented here
                return false;
            case R.id.fragment_menu_item:

                // Do Fragment menu item stuff here
                return true;

            default:
                break;
        }

        return false;
    }
*/

    private void defineView(View view) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Users");
        // recyclerViewDetail= view.findViewById(R.id.detail_recyclerView);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.userlist_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        //Getting user

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiService =
                apiClient.getRetrofit().create(ApiInterface.class);
        //retrofit2.Call<UserListDto> call = apiService.listUsers("0", "5", "0", "0");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Call<List<UserListDto.Content>> call = apiService.listUsers(sharedPref.getLong("loginId",0),
                sharedPref.getLong("customerId",0),sharedPref.getString("token",""),
                null, null, null, null);
        call.enqueue(new Callback<List<UserListDto.Content>>() {
            @Override
            public void onResponse(Call<List<UserListDto.Content>> call, Response<List<UserListDto.Content>> response) {
                if (response.isSuccessful()) {
                    List<UserListDto.Content> userDetails = response.body();//.getContents();
                    userListDto = response.body();//.getContents();
                    userDtoAdapter = new UserDtoAdapter(userDetails, R.layout.user_list_adapter, getContext());
                    userDtoAdapter.setUserClickListener(UserListFragment.this);
                    recyclerView.setAdapter(userDtoAdapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    //Maps the error message in ErrorMessageDto
                    progressBar.setVisibility(View.GONE);
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
                        Toast.makeText(getContext(), errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserListDto.Content>> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.no_response, Toast.LENGTH_SHORT).show();
                Log.d("Error message===",t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void itemClicked(View v, UserListDto.Content userlistDto, int position) {
        final ProgressDialog myProgressDialog;
        myProgressDialog = new ProgressDialog(getContext());
        myProgressDialog.setTitle("Loading");
        myProgressDialog.show();

        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiService = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Call<UserDto> call = apiService.userInfo(sharedPref.getLong("loginId",0), Long.valueOf(userlistDto.getId()),
                sharedPref.getLong("customerId",0),sharedPref.getString("token",""));
        Log.d("id===", userlistDto.getId().toString());
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful()) {
                    UserDto userDetails = response.body();
                    Intent detailsActivity = new Intent(getContext(), UserDetails.class);
                    detailsActivity.putExtra("firstName", userDetails.getUser().getFirstName());
                    detailsActivity.putExtra("lastName", userDetails.getUser().getLastName());
                    detailsActivity.putExtra("email", userDetails.getUser().getEmail());
                    detailsActivity.putExtra("userRole", userDetails.getUser().getUserRole());
                    detailsActivity.putExtra("userName", userDetails.getUser().getUserName());
                    detailsActivity.putExtra("profilePicture",userDetails.getUser().getProfilePicture());

                    startActivity(detailsActivity);
                    myProgressDialog.hide();
                } else {
                    myProgressDialog.hide();
                    //Maps the error message in ErrorMessageDto
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
                        Toast.makeText(getContext(), errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                myProgressDialog.hide();
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //By uv to search students
        newText = newText.toLowerCase();
        List<UserListDto.Content> newList = new ArrayList<>();
        for (UserListDto.Content list : userListDto) {
            String studentName = list.getFirstName().toLowerCase();
            String studentId = String.valueOf(list.getId());
            String studentLastName = list.getLastName().toLowerCase();
            if (studentName.contains(newText) || studentId.contains(newText) || studentLastName.contains(newText)) {
                newList.add(list);
            }
        }
        userDtoAdapter.setFilter(newList);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public void onClick(View v) {

    }


}
