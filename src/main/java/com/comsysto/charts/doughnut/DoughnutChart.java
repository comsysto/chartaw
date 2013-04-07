package com.comsysto.charts.doughnut;

import com.comsysto.charts.Chart;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Doughnut charts are similar to pie charts, however they have the centre cut out, and are therefore shaped more like a doughnut than a pie!
 * They are aso excellent at showing the relational proportions between data.
 * <p/>
 * For a doughnut chart, you must pass in an array of objects with a value and a color property.
 * The value attribute should be a number, Chart.js will total all of the numbers and calculate the relative proportion of each.
 * The color attribute should be a string. Similar to CSS, for this string you can use HEX notation, RGB, RGBA or HSL.
 *
 * @author Daniel Bartl
 */
public class DoughnutChart extends WebMarkupContainer implements Chart {

    /** Boolean - Whether we should show a stroke on each segment */
    public boolean segmentShowStroke = true;
    /** String - The colour of each segment stroke */
    public String segmentStrokeColor = "#fff";
    /** Number - The width of each segment stroke */
    public int segmentStrokeWidth = 2;
    /** The percentage of the chart that we cut out of the middle. */
    public int percentageInnerCutout = 50;
    /** Boolean - Whether we should animate the chart */
    public boolean animation = true;
    /** Number - Amount of animation steps */
    public int animationSteps = 100;
    /** String - Animation easing effect */
    public String animationEasing = "easeOutBounce";
    /** Boolean - Whether we animate the rotation of the Pie */
    public boolean animateRotate = true;
    /** Boolean - Whether we animate scaling the Pie from the centre */
    public boolean animateScale = false;
    /** Function - Will fire on animation completion. */
    public String onAnimationComplete = "";

    private final List<Data> data = new ArrayList<Data>();

    public DoughnutChart(String id) {

        super(id);

        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);

    }

    public DoughnutChart withDataSlice(String value, String color) {

        getData().add(new Data(value, color));
        return this;

    }

    public List<Data> getData() {

        return data;

    }

    public String getDataAsString() {

        StringBuilder out = new StringBuilder();

        out.append("[");

        int i = 0;

        for (Data item : data) {

            out.append(item.getString());

            if (data.size() > i + 1) {

                out.append(",");

            }

            i++;

        }

        out.append("];");

        return out.toString();

    }

    @SuppressWarnings("unchecked")
    public Map<String, ?> getOptions() {

        Map options = new HashMap();

        options.put("segmentShowStroke", segmentShowStroke);
        options.put("segmentStrokeColor", segmentStrokeColor);
        options.put("segmentStrokeWidth", segmentStrokeWidth);
        options.put("percentageInnerCutout", percentageInnerCutout);
        options.put("animation", animation);
        options.put("animationSteps", animationSteps);
        options.put("animationEasing", animationEasing);
        options.put("animateRotate", animateRotate);
        options.put("animateScale", animateScale);
        options.put("onAnimationComplete", onAnimationComplete);

        return options;

    }

    @Override
    public void renderHead(IHeaderResponse response) {

        String varData = "data_" + getMarkupId();
        String varOptions = "options_" + getMarkupId();
        String varCtx = "ctx_" + getMarkupId();
        String varChart = "chart_" + getMarkupId();

        String data = "var " + varData + " = " + getDataAsString();


        String ctx = "var " + varCtx + " = document.getElementById(\"" + getMarkupId() + "\").getContext(\"2d\");\n" +
                "var " + varChart + " = new Chart(" + varCtx + ").Doughnut(" + varData + ", " + varOptions + ");";

        super.renderHead(response);

        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(Chart.class, "Chart.js")));

        final PackageTextTemplate template = new PackageTextTemplate(this.getClass(), "options.template");

        template.interpolate(getOptions());

        response.render(
                OnDomReadyHeaderItem.forScript(
                        data
                                + "\n"
                                + "var " + varOptions + " = "
                                + "\n"
                                + template.getString()
                                + "\n" + ctx));

    }

    public static final class Data {

        private String value;
        private String color;

        public Data(String value, String color) {

            this.color = color;
            this.value = value;

        }

        public String getString() {

            return String.format("{ value: %s, color: '%s' }", value, color);

        }

    }


}