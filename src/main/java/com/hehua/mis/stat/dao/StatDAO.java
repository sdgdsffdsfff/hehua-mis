package com.hehua.mis.stat.dao;

/**
 * Created by huasheng on 9/28/14.
 */

import com.hehua.mis.stat.domain.*;
import org.apache.ibatis.annotations.Select;

import javax.inject.Named;
import java.awt.font.NumericShaper;
import java.util.List;

@Named("statDAO")
public interface StatDAO {
    @Select("select * from summary__order_day" +
            " where channelid not in ('TEST_IOS','TEST_ANDROID')" +
            " and dt between #{start} and #{end}")
    public List<SummaryOrderDay> getSummaryOrderDay(Range period);

    @Select("select * from summary__user_day" +
            " where channelid not in ('TEST_IOS','TEST_ANDROID')" +
            " and dt between #{start} and #{end}")
    public List<SummaryUserDay> getSummaryUserDay(Range period);

    @Select("select * from summary__user_segment_day" +
            " where channelid not like '%TEST%'" +
            " and dt between #{start} and #{end}")
    public List<SummaryUserSegmentDay> getSummaryUserSegmentDay(Range period);

    @Select("select * from summary__kpi_day" +
            " where channelid not in ('TEST_IOS','TEST_ANDROID')" +
            " and dt between #{start} and #{end}")
    public List<SummaryKpiDay> getSummaryKpiDay(Range period);

    @Select("select distinct dt as value from summary__channel_day" +
            " order by dt DESC limit 14 offset 0")
    public List<Label> getDays_DESC();

    @Select("select distinct channelid as value from summary__channel_day" +
            " where channelid not in ('TEST_IOS','TEST_ANDROID')" +
            " and dt between #{start} and #{end}" +
            " order by channelid")
    public List<Label> getChannels(Range period);

    @Select("select distinct yearweek as value from summary__channel_week" +
            " order by yearweek DESC limit 8 offset 0")
    public List<Label> getWeeks_DESC();

    @Select("select max(max_datekey) as value from summary__channel_week" +
            " group by yearweek" +
            " order by yearweek DESC limit 8 offset 0")
    public List<Label> getWeekEndDates_DESC();

    @Select("select distinct channelid as value from summary__channel_week" +
            " where channelid not in ('TEST_IOS','TEST_ANDROID')" +
            " and yearweek between #{start} and #{end}" +
            " order by channelid")
    public List<Label> getChannels_Week(Range period);

    @Select("select * from summary__kpi_week" +
            " where channelid not in ('TEST_IOS','TEST_ANDROID')" +
            " and yearweek between #{start} and #{end}")
    public List<SummaryKpiWeek> getSummaryKpiWeek(Range period);

    @Select("select * from summary__user_segment_week" +
            " where channelid not like '%TEST%'" +
            " and yearweek between #{start} and #{end}")
    public List<SummaryUserSegmentWeek> getSummaryUserSegmentWeek(Range period);

}
