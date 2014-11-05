package com.hehua.mis.controller;

import com.hehua.mis.stat.dao.StatDAO;
import com.hehua.mis.stat.domain.Label;
import com.hehua.mis.stat.domain.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.reverse;
import static java.lang.Integer.parseInt;

/**
 * Created by huasheng on 9/28/14.
 */

@Controller
public class ReportController {
    private static final String CHANNEL_ORDER="report/channelOrder";
    private static final String CHANNEL_USER="report/channelUser";
    private static final String CHANNEL_KPI="report/channelKpi";
    private static final String REPORT_DAILY="report/day";
    private static final String REPORT_WEEKLY="report/week";

    @Autowired
    private StatDAO statDAO;

    @RequestMapping({ "/report/day"})
    public ModelAndView dailyReport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(REPORT_DAILY);

        ArrayList<String> days = toAxis(statDAO.getDays_DESC());
        modelAndView.addObject("xaxis_day", days);
        Range period = getPeriod(days);
        modelAndView.addObject("summaryChannelDayList", statDAO.getChannels(period));
        modelAndView.addObject("summaryKpiDayList",
                statDAO.getSummaryKpiDay(period));
        modelAndView.addObject("summaryUserSegmentDayList",
                statDAO.getSummaryUserSegmentDay(period));
        return modelAndView;
    }

    @RequestMapping({ "/report/week"})
    public ModelAndView weeklyReport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(REPORT_WEEKLY);

        ArrayList<String> weeks = toAxis(statDAO.getWeeks_DESC());
        modelAndView.addObject("xaxis_week", weeks);
        modelAndView.addObject("xaxis_weekenddate", toAxis(statDAO.getWeekEndDates_DESC()));
        Range period = getPeriod(weeks);
        modelAndView.addObject("summaryChannelWeekList", statDAO.getChannels_Week(period));
        modelAndView.addObject("summaryKpiWeekList",
                statDAO.getSummaryKpiWeek(period));
        modelAndView.addObject("summaryUserSegmentWeekList",
                statDAO.getSummaryUserSegmentWeek(period));
        return modelAndView;
    }

    @RequestMapping({ "/report/channelorder"})
    public ModelAndView channelOrder(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(CHANNEL_ORDER);

        ArrayList<String> days = toAxis(statDAO.getDays_DESC());
        modelAndView.addObject("xaxis_day", days);
        Range period = getPeriod(days);
        modelAndView.addObject("summaryOrderDayChannelList", statDAO.getChannels(period));
        modelAndView.addObject("summaryOrderDayList",
                statDAO.getSummaryOrderDay(period));
        return modelAndView;
    }

    @RequestMapping({ "/report/channeluser"})
    public ModelAndView channelUser(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(CHANNEL_USER);

        ArrayList<String> days = toAxis(statDAO.getDays_DESC());
        modelAndView.addObject("xaxis_day", days);
        Range period = getPeriod(days);
        modelAndView.addObject("summaryUserDayChannelList", statDAO.getChannels(period));
        modelAndView.addObject("summaryUserDayList",
                statDAO.getSummaryUserDay(period));
        return modelAndView;
    }

    @RequestMapping({ "/report/channelkpi"})
    public ModelAndView channelKpi(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(CHANNEL_KPI);

        ArrayList<String> days = toAxis(statDAO.getDays_DESC());
        modelAndView.addObject("xaxis_day", days);
        Range period = getPeriod(days);
        modelAndView.addObject("summaryKpiDayChannelList", statDAO.getChannels(period));
        modelAndView.addObject("summaryKpiDayList",
                statDAO.getSummaryKpiDay(period));
        return modelAndView;
    }

    private Range getPeriod(ArrayList<String> t) {
        return new Range(parseInt(t.get(0)), parseInt(t.get(t.size() - 1)));
    }

    private ArrayList<String> toAxis(List<Label> t_desc) {
        ArrayList<String> xarray = new ArrayList<>();
        for (Label label : reverse(t_desc))
            xarray.add(label.getValue());
        return xarray;
    }
}
