<#assign base=request.contextPath />
<!DOCTYPE html>
<html>
    <#include "include/head.ftl"/>
<body class="fixed-left">

<!-- Begin page -->
<div id="wrapper">

    <!-- Top Bar Start -->
    <div class="topbar">

        <!-- LOGO -->
        <div class="topbar-left">
            <a href="index.html" class="logo"><span>Zir<span>cos</span></span><i class="mdi mdi-layers"></i></a>
            <!-- Image logo -->
            <!--<a href="index.html" class="logo">-->
            <!--<span>-->
            <!--<img src="assets/images/logo.png" alt="" height="30">-->
            <!--</span>-->
            <!--<i>-->
            <!--<img src="assets/images/logo_sm.png" alt="" height="28">-->
            <!--</i>-->
            <!--</a>-->
        </div>

        <!-- Button mobile view to collapse sidebar menu -->
        <div class="navbar navbar-default" role="navigation">
            <div class="container">

                <!-- Navbar-left -->
                <ul class="nav navbar-nav navbar-left">
                    <li>
                        <button class="button-menu-mobile open-left waves-effect">
                            <i class="mdi mdi-menu"></i>
                        </button>
                    </li>
                    <li class="hidden-xs">
                        <form role="search" class="app-search">
                            <input type="text" placeholder="Search..."
                                   class="form-control">
                            <a href=""><i class="fa fa-search"></i></a>
                        </form>
                    </li>
                    <li class="hidden-xs">
                        <a href="#" class="menu-item">New</a>
                    </li>
                    <li class="dropdown hidden-xs">
                        <a data-toggle="dropdown" class="dropdown-toggle menu-item" href="#" aria-expanded="false">English
                            <span class="caret"></span></a>
                        <ul role="menu" class="dropdown-menu">
                            <li><a href="#">German</a></li>
                            <li><a href="#">French</a></li>
                            <li><a href="#">Italian</a></li>
                            <li><a href="#">Spanish</a></li>
                        </ul>
                    </li>
                </ul>

                <!-- Right(Notification) -->
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#" class="right-menu-item dropdown-toggle" data-toggle="dropdown">
                            <i class="mdi mdi-bell"></i>
                            <span class="badge up bg-success">4</span>
                        </a>

                        <ul class="dropdown-menu dropdown-menu-right arrow-dropdown-menu arrow-menu-right dropdown-lg user-list notify-list">
                            <li>
                                <h5>导航</h5>
                            </li>
                            <li>
                                <a href="#" class="user-list-item">
                                    <div class="icon bg-info">
                                        <i class="mdi mdi-account"></i>
                                    </div>
                                    <div class="user-desc">
                                        <span class="name">New Signup</span>
                                        <span class="time">5 hours ago</span>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="user-list-item">
                                    <div class="icon bg-danger">
                                        <i class="mdi mdi-comment"></i>
                                    </div>
                                    <div class="user-desc">
                                        <span class="name">New Message received</span>
                                        <span class="time">1 day ago</span>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="user-list-item">
                                    <div class="icon bg-warning">
                                        <i class="mdi mdi-settings"></i>
                                    </div>
                                    <div class="user-desc">
                                        <span class="name">Settings</span>
                                        <span class="time">1 day ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="all-msgs text-center">
                                <p class="m-0"><a href="#">See all Notification</a></p>
                            </li>
                        </ul>
                    </li>

                    <li>
                        <a href="#" class="right-menu-item dropdown-toggle" data-toggle="dropdown">
                            <i class="mdi mdi-email"></i>
                            <span class="badge up bg-danger">8</span>
                        </a>

                        <ul class="dropdown-menu dropdown-menu-right arrow-dropdown-menu arrow-menu-right dropdown-lg user-list notify-list">
                            <li>
                                <h5>Messages</h5>
                            </li>
                            <li>
                                <a href="#" class="user-list-item">
                                    <div class="user-desc">
                                        <span class="name">Patricia Beach</span>
                                        <span class="desc">There are new settings available</span>
                                        <span class="time">2 hours ago</span>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="user-list-item">
                                    <div class="user-desc">
                                        <span class="name">Connie Lucas</span>
                                        <span class="desc">There are new settings available</span>
                                        <span class="time">2 hours ago</span>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="user-list-item">
                                    <div class="user-desc">
                                        <span class="name">Margaret Becker</span>
                                        <span class="desc">There are new settings available</span>
                                        <span class="time">2 hours ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="all-msgs text-center">
                                <p class="m-0"><a href="#">See all Messages</a></p>
                            </li>
                        </ul>
                    </li>

                    <li>
                        <a href="javascript:void(0);" class="right-bar-toggle right-menu-item">
                            <i class="mdi mdi-settings"></i>
                        </a>
                    </li>

                    <li class="dropdown user-box">
                        <a href="" class="dropdown-toggle waves-effect user-link" data-toggle="dropdown" aria-expanded="true">
                            <img src="${base}/static/assets/images/users/avatar-1.jpg" alt="user-img" class="img-circle user-img">
                        </a>

                        <ul class="dropdown-menu dropdown-menu-right arrow-dropdown-menu arrow-menu-right user-list notify-list">
                            <li>
                                <h5>Hi, John</h5>
                            </li>
                            <li><a href="javascript:void(0)"><i class="ti-user m-r-5"></i> Profile</a></li>
                            <li><a href="javascript:void(0)"><i class="ti-settings m-r-5"></i> Settings</a></li>
                            <li><a href="javascript:void(0)"><i class="ti-lock m-r-5"></i> Lock screen</a></li>
                            <li><a href="${base}/logout"><i class="ti-power-off m-r-5"></i> 退出</a></li>
                        </ul>
                    </li>

                </ul> <!-- end navbar-right -->

            </div><!-- end container -->
        </div><!-- end navbar -->
    </div>
    <!-- Top Bar End -->


    <!-- ========== Left Sidebar Start ========== -->
    <div class="left side-menu">
        <div class="sidebar-inner slimscrollleft">

            <!--- Sidemenu -->
            <div id="sidebar-menu">
                <ul>
                    <#if menuList?? && menuList?size gt 0>
                        <#list menuList as obj>
                            <li class="has_sub">
                                <#if obj.children ?? && obj.children?size gt 0>
                                    <a href="javascript:void(0);" class="waves-effect">
                                        <i class="${(obj.icon)!''}"></i>
                                        <span> ${(obj.name)!''}</span>
                                        <span class="menu-arrow"></span>
                                    </a>
                                    <ul class="list-unstyled">
                                        <#list obj.children as obj1>
                                            <li>
                                                <a id="${obj1.id?c}"
                                                   href="javascript:showContent('${obj1.url}','${obj1.id?c}')">
                                                    <i class="${(obj1.icon)!''}"></i>
                                                    <span>${(obj1.name)!''}</span>
                                                </a>
                                            </li>
                                        </#list>
                                    </ul>
                                <#else>
                                    <a id="${obj.id?c}" class="waves-effect"
                                       href="javascript:showContent('${obj.url}','${obj.id?c}')">
                                        <i class="${(obj.icon)!''}"></i>
                                        <span> ${(obj.name)!''}</span>
                                    </a>
                                </#if>
                            </li>
                        </#list>
                    </#if>
                </ul>
            </div>

        </div>
        <!-- Sidebar -left -->

    </div>
    <!-- Left Sidebar End -->

    <!-- ============================================================== -->
    <!-- Start right Content here -->
    <!-- ============================================================== -->
    <div class="content-page">
        <!-- Start content -->
        <div class="content">
            <div class="container" id="main_content">

                <!--<div class="row">
                    <div class="col-xs-12">
                        <div class="page-title-box">
                            <h4 class="page-title">Starter Page </h4>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>-->
                <!-- end row -->

                <!-- Start Page Content -->
                <!--<div class="row" id="main_content">

                </div>-->
                <!-- End PAge Content -->

            </div> <!-- container -->

        </div> <!-- content -->

        <footer class="footer text-right">
            2016 © Zircos.
        </footer>

    </div>

    <!-- ============================================================== -->
    <!-- End Right content here -->
    <!-- ============================================================== -->

</div>
<!-- END wrapper -->

<!-- jQuery  -->
<script src="${base}/static/assets/js/jquery.min.js"></script>
<script src="${base}/static/assets/js/bootstrap.min.js"></script>
<script src="${base}/static/assets/js/detect.js"></script>
<script src="${base}/static/assets/js/fastclick.js"></script>
<script src="${base}/static/assets/js/jquery.blockUI.js"></script>
<script src="${base}/static/assets/js/waves.js"></script>
<script src="${base}/static/assets/js/jquery.slimscroll.js"></script>
<script src="${base}/static/assets/js/jquery.scrollTo.min.js"></script>

<!-- Sweet-Alert  -->
<script src="${base}/static/assets/js/sweet-alert.min.js"></script>
<script src="${base}/static/assets/pages/jquery.sweet-alert.init.js"></script>

<!-- bootstrap-table -->
<script src="${base}/static/assets/js/bootstrap-table.js"></script>
<script src="${base}/static/assets/js/bootstrap-table-treegrid.js"></script>
<script src="${base}/static/assets/js/jquery.treegrid.js"></script>

<!-- ztree -->
<script src="${base}/static/assets/js/jquery.ztree.core.js"></script>
<script src="${base}/static/assets/js/jquery.ztree.excheck.js"></script>

<!-- bootstrap-select -->
<script src="${base}/static/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="${base}/static/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>

<!-- parsley -->
<script src="${base}/static/assets/js/parsley.js"></script>

<!-- Toastr js -->
<script src="${base}/static/plugins/toastr/toastr.min.js"></script>

<!-- select2 js -->
<script src="${base}/static/plugins/select2/js/select2.min.js" type="text/javascript"></script>
<script src="${base}/static/plugins/select2/js/i18n/zh-CN.js" type="text/javascript"></script>

<!-- App js -->
<script src="${base}/static/assets/js/jquery.core.js"></script>
<script src="${base}/static/assets/js/jquery.app.js"></script>
</body>
<script language="javascript">
    var resizefunc = [];
    var menuJson = ${(menuJson)!''};
    var queryFlag = false;
    toastr.options.positionClass = 'toast-bottom-right';

    /*首页加载页面*/
    function showContent(url,id) {
        // var path = '${base}/'+url;
        var path = '${base}' + url;
        $("#main_content").load(path);
        //$(".page-title").text($("#"+id).text());
    }
    /*内页加载页面*/
    function showRight(url) {
        //         var path = '${base}/' + url;
        var path = '${base}' + url;
        $("#main_content").load(path);
    }
    //查询显示和隐藏
    function query(){
        var query = $(".query");
        var search = $(".search");
        if(query.is('.active')){
            search.addClass("hide");
            query.removeClass("active").html("<i class=\"fa fa-filter\"></i> 查询");
            queryFlag = false;
        }else {
            query.addClass("active").html("<i class=\"fa fa-filter\"></i> 隐藏");
            search.removeClass("hide");
            queryFlag = true;
        }
    }
    //重置查询条件
    function reset() {
        document.getElementById("queryForm").reset();
    }
    //记忆查询显示状态
    function showAndHide(){
        var query = $(".query");
        var search = $(".search");
        if(queryFlag){
            query.addClass("active").html("<i class=\"fa fa-filter\"></i> 隐藏");
            search.removeClass("hide");
        }
    }

</script>
</html>