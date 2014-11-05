<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://mis.hehuababy.com/mis/menu" prefix="menu" %>
<%--菜单内容，子啊页面引用菜单，需传入两个参数，分别为L1，要显示的一级菜单，L2，要显示的2级菜单--%>
<div class="container" style="margin-top: 16px;margin-bottom: 8px;">
    <div style="display: none">
        <span id="L1"><%=request.getParameter("L1")%></span>
        <span id="L2"><%=request.getParameter("L2")%></span>
    </div>
    <ul class="nav nav-tabs" role="tablist" id="myTab">
        <menu:menu menuKey="goods" menuLevel="L1"/>
        <menu:menu menuKey="flashes" menuLevel="L1"/>
        <menu:menu menuKey="orders" menuLevel="L1"/>
        <menu:menu menuKey="delivery" menuLevel="L1"/>
        <menu:menu menuKey="versions" menuLevel="L1"/>
        <menu:menu menuKey="users" menuLevel="L1"/>
        <menu:menu menuKey="report" menuLevel="L1"/>
        <menu:menu menuKey="votesManage" menuLevel="L1"/>
    </ul>
    <div class="tab-content" style="padding-top: 20px;" id="tab-content">
        <div class="tab-pane active" id="_goods">
            <div>
                <menu:menu menuKey="good_list" menuLevel="L2" otherAttr="style='width: 72px;'"/>
                <menu:menu menuKey="publish_goods" menuLevel="L2"/>
                <menu:menu menuKey="addBrandGroup" menuLevel="L2" otherAttr="style='width: 72px;'"/>
                <menu:menu menuKey="brand_group_list" menuLevel="L2" otherAttr="style='width: 72px;'"/>
            </div>
        </div>
        <div class="tab-pane" id="_flashes">
            <div>
                <menu:menu menuKey="flash_list" menuLevel="L2"/>
            </div>
        </div>
        <div class="tab-pane" id="_orders">
            <div>
                <menu:menu menuKey="orders_list_all" menuLevel="L2"/>
                <menu:menu menuKey="orders_list_unpay" menuLevel="L2"/>
                <menu:menu menuKey="orders_list_payed" menuLevel="L2"/>
                <menu:menu menuKey="orders_list_deliveried" menuLevel="L2"/>
                <menu:menu menuKey="orders_list_signed" menuLevel="L2"/>
                <menu:menu menuKey="orders_list_refunding" menuLevel="L2"/>
                <menu:menu menuKey="orders_list_refund" menuLevel="L2"/>
                <menu:menu menuKey="orders_list_closed" menuLevel="L2"/>
            </div>
        </div>
        <div class="tab-pane" id="_delivery">
            <div>
                <menu:menu menuKey="companylist" menuLevel="L2" otherAttr="style='width: 100px;'" />
                <menu:menu menuKey="paiyou_switch" menuLevel="L2" leftSpace="33px"/>
            </div>
        </div>
        <div class="tab-pane" id="_versions">
            <div>
                <menu:menu menuKey="version_control" menuLevel="L2"/>
            </div>
        </div>
        <div class="tab-pane" id="_users">
            <div>
                <menu:menu menuKey="users_list" menuLevel="L2"/>
                <menu:menu menuKey="addTarento" menuLevel="L2"/>
                <menu:menu menuKey="tarentoList" menuLevel="L2"/>
            </div>
        </div>
        <div class="tab-pane" id="_report">
            <div>
                <menu:menu menuKey="report_day" menuLevel="L2"/>
                <menu:menu menuKey="report_week" menuLevel="L2"/>
                <%--<menu:menu menuKey="report_channel_kpi" menuLevel="L2" otherAttr="style='width: 100px;'"/>--%>
                <%--<div class="col-md-1" style="margin-left: 33px;"><a class="btn btn-block" href="/report/channelorder"--%>
                                                                    <%--id="report_channel_order">渠道-订单</a></div>--%>
                <%--<menu:menu menuKey="report_channel_user" menuLevel="L2"/>--%>
            </div>
        </div>
        <div class="tab-pane" id="_votesManage">
            <div>
                <menu:menu menuKey="vote_list" menuLevel="L2"/>
                <menu:menu menuKey="vote_audit_apply" menuLevel="L2"/>
                <menu:menu menuKey="vote_send_good" menuLevel="L2"/>
                <menu:menu menuKey="vote_audit_appraise" menuLevel="L2"/>
                <menu:menu menuKey="vote_new_audit" menuLevel="L2"/>
            </div>
        </div>
    </div>
</div>