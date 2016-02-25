package org.pragyan.pragyantshirtapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String eventName;
    EventInfo eventInfo;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String eventName) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, eventName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventsAdapter adapter = new EventsAdapter(getActivity());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        fTextView eventNameView = (fTextView) v.findViewById(R.id.detailText);
        eventNameView.setText(eventName);
        fTextView eventDescription = (fTextView) v.findViewById(R.id.descriptionDetail);
        eventInfo = adapter.getEventInfo(eventName);
        eventDescription.setText(eventInfo.description);
//        Button directions = (Button) v.findViewById(R.id.eventDirectionButton);
        Button location = (Button) v.findViewById(R.id.eventLocationButton);
        if(eventInfo.venue.equals("Online")) {
//            directions.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
        }
//
//        directions.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+eventInfo.locy+","+eventInfo.locx+"&mode=w");
//                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                        mapIntent.setPackage("com.google.android.apps.maps");
//                        startActivity(mapIntent);
//                    }
//                }
//        );
        location.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+eventInfo.locy+","+eventInfo.locx+"("+eventInfo.venue+")");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

        }
        });

        return v;
    }

    // Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
