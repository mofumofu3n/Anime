package com.ichif1205.anime.request;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.model.Lineup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineupRequest extends JsonObjectRequest {

    public LineupRequest(String url) {
        super(Method.GET, url, null, null, null);
    }


    @Override
    protected void deliverResponse(JSONObject response) {
        final ArrayList<Lineup> lineupList = new ArrayList<>();

        try {
            final JSONObject responses = response.getJSONObject("response");
            final JSONArray items = responses.getJSONArray("item");

            final int length = items.length();
            for (int i = 0; i < length; i++) {
                final JSONObject row = items.getJSONObject(i);
                final Lineup lineup = new Lineup(row);
                lineupList.add(lineup);
            }

        } catch (JSONException e) {
            deliverError(new VolleyError(e));
            return;
        }
        BusHolder.get().post(new SuccessEvent(lineupList));
    }

    @Override
    public void deliverError(VolleyError error) {
        BusHolder.get().post(new ErrorEvent(error));
    }

    public class SuccessEvent {
        private final List<Lineup> mList;

        public SuccessEvent(List<Lineup> list) {
            mList = list;
        }

        public List<Lineup> getList() {
            return mList;
        }
    }

    public class ErrorEvent {
        private final VolleyError mError;

        public ErrorEvent(VolleyError error) {
            mError = error;
        }

        public VolleyError getError() {
            return mError;
        }
    }
}
