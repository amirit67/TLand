package ir.touristland.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.touristland.Application;
import ir.touristland.R;


/**
 * This is a slider with a description TextView.
 */
public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);
        ImageView target = v.findViewById(R.id.daimajia_slider_image);
        TextView description = v.findViewById(R.id.description);
        description.setTypeface(Application.font);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}
