package masterung.androidthai.in.th.sunteenfriend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import masterung.androidthai.in.th.sunteenfriend.R;
import masterung.androidthai.in.th.sunteenfriend.ServiceActivity;
import masterung.androidthai.in.th.sunteenfriend.utility.GetAllData;
import masterung.androidthai.in.th.sunteenfriend.utility.MyAlertDialog;
import masterung.androidthai.in.th.sunteenfriend.utility.MyConstant;

public class MainFragment extends Fragment{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();

    }   // Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String userString = userEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();

                if (userString.isEmpty() || passwordString.isEmpty()) {

                    MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                    myAlertDialog.normalDialog("Have Space",
                            "Please Fill EveryBlank");

                } else {

                    MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                    MyConstant myConstant = new MyConstant();
                    boolean aBoolean = true;
                    String truePassword = null;

                    try {

                        GetAllData getAllData = new GetAllData(getActivity());
                        getAllData.execute(myConstant.getUrlGetAllUser());

                        String jsonString = getAllData.get();
                        Log.d("8JulyV1", "JSON ==> " + jsonString);

                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i=0; i<jsonArray.length(); i+=1) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (userString.equals(jsonObject.getString("User"))) {
                                truePassword = jsonObject.getString("Password");
                                aBoolean = false;
                            }
                        }

                        if (aBoolean) {
                            myAlertDialog.normalDialog("User False",
                                    "No " + userString + " in my Database");
                        } else if (passwordString.equals(truePassword)) {
//                            Password True
                            Toast.makeText(getActivity(), "Welcome to my App",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), ServiceActivity.class));
                            getActivity().finish();

                        } else {
                            myAlertDialog.normalDialog("Password False",
                                    "Please Try Again Password Flase");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }




                }   // if



            }
        });
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Replace Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}   // Main Class
