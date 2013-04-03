package com.comsysto;

import com.comsysto.charts.PieChart;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {

        super(parameters);

        PieChart pie = new PieChart("pie");

        pie
                .withDataSlice("30", "#F38630")
                .withDataSlice("50", "#E0E4CC")
                .withDataSlice("100", "#69D2E7");

        add(pie);

    }

}
