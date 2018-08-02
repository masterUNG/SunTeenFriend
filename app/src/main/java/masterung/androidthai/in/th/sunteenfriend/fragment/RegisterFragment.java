package masterung.androidthai.in.th.sunteenfriend.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import masterung.androidthai.in.th.sunteenfriend.MainActivity;
import masterung.androidthai.in.th.sunteenfriend.R;
import masterung.androidthai.in.th.sunteenfriend.utility.MyAlertDialog;
import masterung.androidthai.in.th.sunteenfriend.utility.MyConstant;
import masterung.androidthai.in.th.sunteenfriend.utility.UploadDataToServer;

public class RegisterFragment extends Fragment {

    //    Explicit
    private Uri uri;
    private ImageView imageView;
    private boolean aBoolean = true;
    private String imageString;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        Picture Controller
        pictureController();

    }   // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemUpload) {

            uploadAnUpdateValue();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void uploadAnUpdateValue() {

        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText userEditText = getView().findViewById(R.id.edtUser);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

//        Get Value to String
        String nameString = nameEditText.getText().toString().trim();
        String userString = userEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();


//        Check Choose Image and Space
        if (aBoolean) {
//            Non Choose Image
            myAlertDialog.normalDialog("Non Choose Image",
                    "Please Choose Image");
        } else if (nameString.isEmpty() || userString.isEmpty() || passwordString.isEmpty()) {
//            Have Space
            myAlertDialog.normalDialog("Have Space",
                    "Please Fill All Blank");
        } else {
//            No Space
            uploadImage();
            uploadText(nameString, userString, passwordString);

        }


    }   // upload

    private void uploadText(String nameString, String userString, String passwordString) {

        MyConstant myConstant = new MyConstant();

        imageString = myConstant.getUrlImage() + imageString;

        try {

            UploadDataToServer uploadDataToServer = new UploadDataToServer(getActivity());
            uploadDataToServer.execute(nameString, userString, passwordString,
                    imageString, myConstant.getUrlAddUser());

            if (Boolean.parseBoolean(uploadDataToServer.get())) {
                Toast.makeText(getActivity(), "Success Register", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Cannot Register", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void uploadImage() {

//        Find Path Image
        String pathString = null;
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, strings,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathString = cursor.getString(index);
        } else {
            pathString = uri.getPath();
        }

        Log.d("8JulyV1", "Path ==> " + pathString);

//        Find imageString
        imageString = pathString.substring(pathString.lastIndexOf("/"));
        Log.d("8JulyV1", "imageString ==> " + imageString);

//        Change Policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Use Library ftp4j
        File file = new File(pathString);
        MyConstant myConstant = new MyConstant();
        FTPClient ftpClient = new FTPClient();

        try {
//            Evant A
            ftpClient.connect(myConstant.getHostFTP(), myConstant.getPotrFTP());
            ftpClient.login(myConstant.getUserFTP(), myConstant.getPasswordFTP());
            ftpClient.setType(FTPClient.TYPE_BINARY);
            ftpClient.changeDirectory("MasterUNG");
            ftpClient.upload(file, new uploadListener());

        } catch (Exception e) {
            e.printStackTrace();
            try {
//                Event B
                ftpClient.disconnect(true);

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }


    }

    public class uploadListener implements FTPDataTransferListener{
        @Override
        public void started() {
            Toast.makeText(getActivity(), "Start Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void transferred(int i) {
            Toast.makeText(getActivity(), "Process Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void completed() {
            Toast.makeText(getActivity(), "Complete Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void aborted() {

        }

        @Override
        public void failed() {

        }
    }




    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
//            Success Choose Image
            uri = data.getData();
            aBoolean = false;
//            Show Image on ImageView
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 800, 600, false);
                imageView.setImageBitmap(bitmap1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Please Choose Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void pictureController() {
        imageView = getView().findViewById(R.id.imvPicture);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent to Other App
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Choose App View Image"),
                        1);

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register, menu);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

//        Setup Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.register));
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Fill All Blank");

//        Setup Navigator Icon
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
