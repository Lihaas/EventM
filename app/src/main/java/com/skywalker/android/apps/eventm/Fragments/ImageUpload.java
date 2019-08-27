package com.skywalker.android.apps.eventm.Fragments;


import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skywalker.android.apps.eventm.MainActivity;
import com.skywalker.android.apps.eventm.Model.DetailPojo;
import com.skywalker.android.apps.eventm.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageUpload extends Fragment {

    ImageView image_upload;
    TextView upload;
    final  int PICK_IMAGE = 1;
    Uri imageUri;
    EditText mName,mProfesion;
    String imgg,UserProfesion,UserName;
    int ventId;
    String mId,pNumber;

    private StorageReference mStorageRef;
    DatabaseReference myRef;
    FirebaseDatabase database;
    ProgressDialog dialog;

    public ImageUpload() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image_upload, container, false);

        image_upload = rootView.findViewById(R.id.image_want_change);
        upload = rootView.findViewById(R.id.upload_Image);
        mName = rootView.findViewById(R.id.editText_name);
        mProfesion = rootView.findViewById(R.id.editText_profesion);

        mStorageRef = FirebaseStorage.getInstance().getReference("Profile_pic");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");


        try{
            Bundle bundle = this.getArguments();
            imgg = bundle.getString("img");
            UserName = bundle.getString("name");
            UserProfesion = bundle.getString("profesion");
            ventId = bundle.getInt("eventId");
            pNumber = bundle.getString("phone");
            mId = bundle.getString("mId");

            Picasso.get().load(imgg).into(image_upload);
        }catch (Exception e){}

        mName.setText(UserName);
        mProfesion.setText(UserProfesion);




        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chossingImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Uploading Your Image.......");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });




        return rootView;
    }

    public void chossingImage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,PICK_IMAGE);

    }

    //To show the image on Screen
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == -1 && data!= null && data.getData()!= null){
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(image_upload);
            image_upload.setImageURI(imageUri);
        }


    }

    //TO find the extention
    private String getFIleExtention(Uri uri){
        ContentResolver Crso = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(Crso.getType(uri));


    }

    public void uploadImage(){
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(imageUri != null){
            //To set the Image Extention and name(in milli sec. beacuse we doesnt want that similar data ho)
            final StorageReference reference = mStorageRef.child(System.currentTimeMillis()+"."+getFIleExtention(imageUri));
            reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                           reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   DetailPojo dp = new DetailPojo(mName.getText().toString(),pNumber,mProfesion.getText().toString(),uri.toString(),ventId,mId);
                                  myRef.child(currentuser).setValue(dp);

                               }
                           });


                         /* //  progressBar.setVisibility(View.INVISIBLE);
                            DetailPojo upload = new DetailPojo();
                            upload.setmImageUrl(taskSnapshot.getUploadSessionUri().toString());
                           reference.getDownloadUrl();
                           // String ulpoadID = myRef.getKey();
                            myRef.child(currentuser).setValue(upload.getmImageUrl());*/
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }else{
            DetailPojo dp = new DetailPojo(mName.getText().toString(),pNumber,mProfesion.getText().toString(),imgg,ventId,mId);
            myRef.child(currentuser).setValue(dp);
            Toast.makeText(getActivity(), "Your info updated", Toast.LENGTH_SHORT).show();
            Profile fragment = new Profile();
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_up,
                            R.animator.slide_down,
                            R.animator.slide_up,
                            R.animator.slide_down)
                    .replace(R.id.fragments, fragment)
                    .commit();
        }


    }


}
