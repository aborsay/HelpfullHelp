package net.blueridge.apps.resource_full;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aborsay on 10/2/2018.
 */

public class FragmentContactUs extends Fragment {

    public FragmentContactUs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contactus, container, false);
        view.findViewById(R.id.bugEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBugsEmail();
            }

        });
        return view;
    }


    private void sendBugsEmail(){

                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "To: bugs@resource-full.com, ");
                intent.putExtra(Intent.EXTRA_TEXT, "Dear Resource Full, ");
                intent.setData(Uri.parse("mailto: bugs@resource-full.com"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


    }


}
