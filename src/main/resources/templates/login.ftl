<#assign base=request.contextPath />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">
    <!-- App favicon -->
    <link rel="shortcut icon" href="${base}/static/assets/images/favicon.ico">
    <!-- App title -->
    <title>Zircos - Responsive Admin Dashboard Template</title>
    <!-- Sweet Alert -->
    <link href="${base}/static/assets/css/sweet-alert.css" rel="stylesheet" type="text/css">
    <!-- App css -->
    <link href="${base}/static/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/assets/css/core.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/assets/css/components.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/assets/css/icons.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/assets/css/pages.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/assets/css/responsive.css" rel="stylesheet" type="text/css" />

    <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script src="${base}/static/assets/js/modernizr.min.js"></script>
</head>

<body class="bg-transparent">

<!-- HOME -->
<section>
    <div class="container-alt">
        <div class="row">
            <div class="col-sm-12">

                <div class="wrapper-page">

                    <div class="m-t-40 account-pages">
                        <div class="text-center account-logo-box">
                            <h2 class="text-uppercase">
                                <span><img src="${base}/static/assets/images/logo.png" alt="" height="36"></span>
                            </h2>
                        </div>
                        <div class="account-content">
                            <#if msg ??>
                                <div class="alert alert-icon alert-danger alert-dismissible fade in" role="alert">
                                    <button type="button" class="close" data-dismiss="alert"
                                            aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                    <i class="mdi mdi-block-helper"></i>
                                    ${(msg)!''}
                                </div>
                            </#if>
                            <form class="form-horizontal" action="/login" method="post">

                                <div class="form-group ">
                                    <div class="col-xs-12">
                                        <input class="form-control" type="text" name="username" required="" placeholder="Username" value="admin">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <input class="form-control" type="text" name="password" required="" placeholder="Password" value="123456">
                                    </div>
                                </div>

                                <div class="form-group account-btn text-center m-t-20">
                                    <div class="col-xs-12">
                                        <button class="btn w-md btn-bordered btn-danger waves-effect waves-light" type="submit">Log In</button>
                                    </div>
                                </div>

                            </form>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <!-- end card-box-->

                </div>
                <!-- end wrapper -->

            </div>
        </div>
    </div>
</section>
<!-- END HOME -->

<!-- jQuery  -->
<script src="${base}/static/assets/js/jquery.min.js"></script>
<script src="${base}/static/assets/js/bootstrap.min.js"></script>
<script src="${base}/static/assets/js/detect.js"></script>
<script src="${base}/static/assets/js/fastclick.js"></script>
<script src="${base}/static/assets/js/jquery.blockUI.js"></script>
<script src="${base}/static/assets/js/waves.js"></script>
<script src="${base}/static/assets/js/jquery.slimscroll.js"></script>
<script src="${base}/static/assets/js/jquery.scrollTo.min.js"></script>

<!-- App js -->
<script src="${base}/static/assets/js/jquery.core.js"></script>
</body>
</html>