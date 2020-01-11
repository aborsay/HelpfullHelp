package net.blueridge.apps.resource_full;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentAboutUs extends Fragment {
    public FragmentAboutUs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        view.findViewById(R.id.emailBorsay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enailBorsay();
            }

        });
        return view;
    }

    private void enailBorsay(){

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "To: george@borsay.xyz, ");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello Aaron, ");
        intent.setData(Uri.parse("mailto: george@borsay.xyz"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }
}


