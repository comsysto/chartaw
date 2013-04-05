package com.comsysto;

import com.comsysto.charts.PieChart;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {

        super(parameters);

        PieChart pie1 = new PieChart("pie1");

        pie1
                .withDataSlice("30", "#F38630")
                .withDataSlice("50", "#E0E4CC")
                .withDataSlice("100", "#69D2E7");

        add(pie1);

        PieChart pie2 = new PieChart("pie2");

        pie2
                .withDataSlice("10", "#F38630")
                .withDataSlice("10", "#E0E4CC")
                .withDataSlice("200", "#69D2E7");

        pie2.animation = false;
        pie2.animateScale = false;
        pie2.animateRotate = false;
        pie2.segmentShowStroke = false;
        pie2.segmentStrokeColor = "#ccc";
        add(pie2);

    }

}
