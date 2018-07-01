package com.example.justin.simpletwitter.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.justin.simpletwitter.R;
import com.example.justin.simpletwitter.fragment.home.TabLayoutFragment;
import com.example.justin.simpletwitter.utils.AppInfo;
import com.example.justin.simpletwitter.utils.TwitterAPI;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;

/**
 * Fragment that represents posting a tweet.
 */
public class ComposeFragment extends Fragment {

    public static final String TAG = "ComposeFragment";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static OAuth10aService service = AppInfo.getService();
    private static AppInfo appInfo = AppInfo.getInstance();

    private Button btnPost, btnAddPhoto;

    private EditText etContent;

    private String mCurrentPhotoPath;

    public ComposeFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compose_tweet, container, false);

        etContent = view.findViewById(R.id.et_compose_tweet);
        if (getArguments() != null) {
            String s = "@" + getArguments().getString("user_name");
            etContent.setText(s);
        }

        btnPost = view.findViewById(R.id.btn_post_tweet);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString();
                PostTweet post = new PostTweet();
                post.execute(content);
                TabLayoutFragment fragment = new TabLayoutFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, fragment).addToBackStack(null).commit();
            }
        });

        btnAddPhoto = view.findViewById(R.id.btn_add_photo);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            // Opens the camera
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {
                    File f = getImage();
                    if(f != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.example.android.fileprovider",
                                f);
                        i.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                    }
                }

            }
        });

        return view;
    }

    // Saves the result of the activity.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            if(b != null) {
                Bitmap img = (Bitmap) b.get("data");
                Log.d(TAG, "onActivityResult: " + img.getByteCount());
            } else {
                Log.d(TAG, "data: " + b.get("data"));
                Log.d(TAG, "bundle: " + b);

            }
        }
    }

    // Saves the image in a file.
    private File getImage() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".png", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "getImage: " + mCurrentPhotoPath);
        return image;
    }

    /**
     * Task for adding a picture to a tweet
     */
    private class AddMedia extends AsyncTask<Bitmap, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Bitmap... bitmaps) {

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }

    /**
     * Task for posting a tweet.
     */
    class PostTweet extends AsyncTask<String, Void, Void> {

        /**
         * Posts the tweet.
         * @param strings
         * @return all the nothing.
         */
        @Override
        protected Void doInBackground(String... strings) {
            try {
                String encoded = URLEncoder.encode(strings[0], "UTF-8");
                Log.d(TAG, "doInBackground: " + encoded);
                String url = TwitterAPI.STATUSES_UPDATE + encoded;
                OAuthRequest request = new OAuthRequest(Verb.POST, url);
                service.signRequest(AppInfo.getAccessToken(), request);
                Response r = service.execute(request);
                if(!r.isSuccessful())
                    //TODO: implement tweeting failed lil toast or something :*
                    return null;
            } catch (InterruptedException | IOException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
