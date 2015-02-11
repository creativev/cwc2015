package co.creativev.cwc2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ListView listView = new ListView(getActivity());
            listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            listView.setAdapter(new MatchesAdapter(inflater));
            return listView;
        }

        private static class MatchesAdapter extends BaseAdapter {
            private final LayoutInflater inflater;

            private MatchesAdapter(LayoutInflater inflater) {
                this.inflater = inflater;
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.fragment_main, parent, false);
                    ViewHolder viewHolder = new ViewHolder(
                            ((TextView) convertView.findViewById(R.id.txtTime)),
                            ((ImageView) convertView.findViewById(R.id.imgCountry1)),
                            ((TextView) convertView.findViewById(R.id.txtCountry1)),
                            ((TextView) convertView.findViewById(R.id.txtScore1)),
                            ((TextView) convertView.findViewById(R.id.txtOvers1)),
                            ((ImageView) convertView.findViewById(R.id.imgCountry2)),
                            ((TextView) convertView.findViewById(R.id.txtCountry2)),
                            ((TextView) convertView.findViewById(R.id.txtScore2)),
                            ((TextView) convertView.findViewById(R.id.txtOvers2)),
                            ((TextView) convertView.findViewById(R.id.txtVenue)),
                            ((TextView) convertView.findViewById(R.id.txtMatchResult))
                    );
                    convertView.setTag(viewHolder);
                }

                return convertView;
            }
        }
    }

    public static class ViewHolder {

        private final TextView time;
        private final ImageView imgCountry1;
        private final TextView country1;
        private final TextView score1;
        private final TextView overs1;
        private final ImageView imgCountry2;
        private final TextView country2;
        private final TextView score2;
        private final TextView overs2;
        private final TextView venue;
        private final TextView result;

        public ViewHolder(TextView time, ImageView imgCountry1, TextView country1, TextView score1, TextView overs1, ImageView imgCountry2, TextView country2, TextView score2, TextView overs2, TextView venue, TextView result) {
            this.time = time;
            this.imgCountry1 = imgCountry1;
            this.country1 = country1;
            this.score1 = score1;
            this.overs1 = overs1;
            this.imgCountry2 = imgCountry2;
            this.country2 = country2;
            this.score2 = score2;
            this.overs2 = overs2;
            this.venue = venue;
            this.result = result;
        }
    }
}
