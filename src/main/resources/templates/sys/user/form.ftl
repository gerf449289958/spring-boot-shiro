<#assign base=request.contextPath />
<!DOCTYPE html>
<div class="row">
    <div class="col-xs-12">
        <div class="page-title-box">
            <h4 class="page-title m-t-10">用户编辑 </h4>
            <ol class="breadcrumb p-0 m-0">
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<div class="col-sm-12">
    <div class="card-box">
        <form id="postForm" data-parsley-validate>
            <input type="hidden" id="id" name="id" value="${(sysUser.id?c)!''}">
            <div class="row">
                <div class="col-md-12 ">

                    <div class="form-group">
                        <label for="username">登录账户<span class="text-danger">*</span></label>
                        <input type="text" id="username" name="username" parsley-trigger="change" required="" class="form-control" value="${(sysUser.username)!''}"
                               data-parsley-length="[2, 15]"
                               data-parsley-length-message="登录名长度需要在2-15个字之间">
                    </div>
                    <div class="form-group">
                        <label for="name">用户名称<span class="text-danger">*</span></label>
                        <input type="text" id="name" name="name" parsley-trigger="change" required="" class="form-control" value="${(sysUser.name)!''}"
                               data-parsley-length="[2, 15]"
                               data-parsley-length-message="用户名称长度需要在2-15个字之间">
                    </div>
                    <div class="form-group">
                        <label for="remarks">备注</label>
                        <input type="text" name="remarks" parsley-trigger="change" class="form-control" id="sort" value="${(sysUser.remarks)!''}"
                               data-parsley-maxlength="200"
                               data-parsley-maxlength-message="备注长度需要在200个字以内">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-offset-5 col-sm-9">
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-primary" id="submit">
                        <i class="fa fa-check"></i> 保存
                    </button>
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-success" onclick="showRight('${base}/sys/user/list.page');">
                        <i class="fa fa-undo"></i> 返回
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var listUrl = "${base}/sys/user/list.page";
    var saveUrl = "${base}/sys/user/save.json";
    var updareUrl = "${base}/sys/user/update.json";
    //表单验证初始化
    var $parsley = $('#postForm').parsley();
    $("#submit").click(function() {
        var id = $("#id").val();
        var flag = $parsley.validate()
        if(!flag){
            return;
        }
        if(id){
            update();
        }else {
            save();
        }
    });
    //保存
    function save(){
        $.post(saveUrl, $("#postForm").serialize(), function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load(listUrl);
            }else{
                swal(data.msg, "", "error");
            }
        });
    }
    //更新
    function update(){
        $.post(updareUrl, $("#postForm").serialize(), function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load(listUrl);
            }else{
                swal(data.msg, "", "error");
            }
        });
    }
</script>