package co.creativev.cwc2015;

import android.app.Activity;
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

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MainActivity extends ActionBarActivity {

    private Datasource dataSource;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d, EEEE");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            dataSource = OpenHelperManager.getDataSource(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MatchesFragment())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenHelperManager.releaseHelper();
    }

    public static class MatchesFragment extends Fragment {

        private Datasource dataSource;

        public MatchesFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            dataSource = ((MainActivity) activity).dataSource;
        }

        @Override
        public void onDetach() {
            super.onDetach();
            dataSource = null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ListView listView = new ListView(getActivity());
            listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            listView.setAdapter(new MatchesAdapter(inflater, dataSource));
            return listView;
        }

        private static class MatchesAdapter extends BaseAdapter {
            private final LayoutInflater inflater;
            private final Datasource dataSource;

            private MatchesAdapter(LayoutInflater inflater, Datasource dataSource) {
                this.inflater = inflater;
                this.dataSource = dataSource;
            }

            @Override
            public int getCount() {
                return dataSource.getCount();
            }

            @Override
            public Match getItem(int position) {
                return dataSource.getByPosition(position);
            }

            @Override
            public long getItemId(int position) {
                return getItem(position).id;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.fragment_main, parent, false);
                    ViewHolder viewHolder = new ViewHolder(
                            ((TextView) convertView.findViewById(R.id.txtDate)),
                            ((TextView) convertView.findViewById(R.id.txtTime)),
                            ((TextView) convertView.findViewById(R.id.txtVs)),
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

                Match match = getItem(position);
                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.date.setText(DATE_FORMAT.format(match.time));
                viewHolder.time.setText(TIME_FORMAT.format(match.time));
                if (isPresent(match.country1)) {
                    viewHolder.imgCountry1.setImageResource(getDrawableResource(match.country1.toLowerCase()));
                    viewHolder.country1.setText(match.country1);
                    viewHolder.vs.setText(R.string.txt_vs);
                } else {
                    viewHolder.imgCountry1.setImageResource(getDrawableResource("none"));
                    viewHolder.country1.setText(R.string.empty);
                    viewHolder.vs.setText(match.name);
                }
                if (isPresent(match.score1, match.overs1)) {
                    viewHolder.score1.setVisibility(VISIBLE);
                    viewHolder.overs1.setVisibility(VISIBLE);
                    viewHolder.score1.setText(match.score1);
                    viewHolder.overs1.setText(match.overs1);
                } else {
                    viewHolder.score1.setVisibility(GONE);
                    viewHolder.overs1.setVisibility(GONE);
                }
                if (isPresent(match.country2)) {
                    viewHolder.imgCountry2.setImageResource(getDrawableResource(match.country2.toLowerCase()));
                    viewHolder.country2.setText(match.country2);
                } else {
                    viewHolder.imgCountry2.setImageResource(getDrawableResource("none"));
                    viewHolder.country2.setText(R.string.empty);
                }
                if (isPresent(match.score2, match.overs2)) {
                    viewHolder.score2.setVisibility(VISIBLE);
                    viewHolder.overs2.setVisibility(VISIBLE);
                    viewHolder.score2.setText(match.score2);
                    viewHolder.overs2.setText(match.overs2);
                } else {
                    viewHolder.score2.setVisibility(GONE);
                    viewHolder.overs2.setVisibility(GONE);
                }
                viewHolder.venue.setText(match.venue);
                if (isPresent(match.result)) {
                    viewHolder.result.setVisibility(VISIBLE);
                    viewHolder.result.setText(match.result);
                } else {
                    viewHolder.result.setVisibility(GONE);
                }
                return convertView;
            }

            private boolean isPresent(String... fields) {
                for (String s : fields) {
                    if (s == null || s.trim().isEmpty()) return false;
                }
                return true;
            }
        }
    }

    public static class ViewHolder {

        private final TextView date;
        private final TextView time;
        private final TextView vs;
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

        public ViewHolder(TextView date, TextView time, TextView vs, ImageView imgCountry1, TextView country1, TextView score1, TextView overs1,
                          ImageView imgCountry2, TextView country2, TextView score2, TextView overs2,
                          TextView venue, TextView result) {
            this.date = date;
            this.time = time;
            this.vs = vs;
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

    public static int getDrawableResource(String resourceId) {
        try {
            Field idField = R.mipmap.class.getDeclaredField(resourceId);
            return idField.getInt(idField);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
