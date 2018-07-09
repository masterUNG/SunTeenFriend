package masterung.androidthai.in.th.sunteenfriend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import masterung.androidthai.in.th.sunteenfriend.R;
import masterung.androidthai.in.th.sunteenfriend.utility.FriendAdapter;
import masterung.androidthai.in.th.sunteenfriend.utility.GetAllData;
import masterung.androidthai.in.th.sunteenfriend.utility.MyConstant;

public class ServiceFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create RecyclerView
        createRecyclerView();

    }   // Main Method

    private void createRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewFriend);
        MyConstant myConstant = new MyConstant();
        try {

            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlGetAllUser());

            String jsonString = getAllData.get();
            ArrayList<String> nameStringArrayList = new ArrayList<>();
            ArrayList<String> messageStringArrayList = new ArrayList<>();
            ArrayList<String> iconStringArrayList = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStringArrayList.add(jsonObject.getString("Name"));
                messageStringArrayList.add(jsonObject.getString("Message"));
                iconStringArrayList.add(jsonObject.getString("Image"));
            }

            FriendAdapter friendAdapter = new FriendAdapter(getActivity(), nameStringArrayList,
                    messageStringArrayList, iconStringArrayList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setAdapter(friendAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;
    }
}
