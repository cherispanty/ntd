<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/table.css" />
    <link rel="stylesheet" href="css/chosen.css" />
    <link rel="stylesheet" type="text/css" href="css/bootstrap-datetimepicker.min.css" media="screen">
    <script src="http://libs.baidu.com/jquery/2.0.1/jquery.min.js"></script>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/layer/layer.js"></script>
    <script type="text/javascript" src="/laydate/laydate.js"></script>
    <script type="text/javascript" src="/js/Date.js"></script>
    <script type="text/javascript" src="/js/chosen.js"></script>
    <script src="/js/bootstrap-datetimepicker.js" type="text/javascript" charset="utf-8"></script>
    <script src="/js/bootstrap-datetimepicker.zh-CN.js" type="text/javascript" charset="utf-8"></script>
    <script src="/js/bootstrap-datetimepicker.fr.js" type="text/javascript" charset="utf-8"></script>
    <script >
        $(function() {
            laydate.render({elem: '#dutyRecoredDate'});
            laydate.render({elem: '#addDate'});

            //月小结  协助人下拉
            $("#addrecord").html("")
            $("#updrecord").html("")
            $.ajax({
                url: '/getUser',
                dataType: 'json',
                success: function (data) {
                    var str;
                    console.log(json)
                    var json = data.data
                    for (var i in json) {
                        str = '<option class="assisManItem" value="' + json[i].fullName + '">' + json[i].fullName + '</option>';
                        $("#addrecord").append(str)
                        $("#updrecord").append(str)
                    }
                    $("#addrecord").trigger("liszt:updated");
                    $("#updrecord").trigger("liszt:updated");
                    $("#addrecord").chosen({
                        no_results_text:'未找到',
                    });
                    $("#updrecord").chosen({
                        no_results_text:'未找到',
                    });
                }
            })

            //初始化
            inittable();

            //查询
            $("#query").click(function () {
                inittable()
            })

            //添加值班记录
            $('#addDutyRecord').unbind('click').click(function () {
                checkAddInput()
                var recordId=$("#addDate").val().replace(/\-/g, '')
                $.ajax({
                    url: "/addDutyRecord",
                    dataType: 'json',
                    data: {
                        recordId:recordId,
                        empName: $("#addempName").val(),
                        customerServiceName:$("#addcustomerServiceName").val(),
                        jiraId:$("#addjiraId").val()
                    },
                    async:false,
                    success: function (data) {
                        if (data.message == "添加成功") {
                            layer.msg('添加成功!', {
                                icon: 1,
                                time: 1000
                            });
                            setTimeout(function wlh() {
                                window.location.href = "/dutyRecord"
                            }, 500)
                        } else if (data.message == "禁止提前创建值班记录") {
                            layer.msg("禁止提前创建值班记录");
                        } else if (data.message == "重复添加") {
                            layer.msg("禁止值班记录重复添加");
                        } else {
                            layer.msg("添加失败");
                        }
                    }
                });
            })
        })

        function inittable() {
            var recordId=$("#dutyRecoredDate").val().replace(/\-/g, '')
            $.ajax({
                type: 'get',
                url: 'getDutyRecord',
                dataType: 'json',
                data: {
                    recordId:recordId
                },
                success: function (data) {
                    $("#tbody").html("");
                    var json = data.data
                    $('#username').html('欢迎 ' + '<font color="red">' + data.fullName + '</font>' + ' 登录米仓 值班记录');
                    for (var i in json) {
                        var tr = $('<tr>');
                        tr.append($('<td>').html(json[i].recordId))
                        tr.append($('<td>').html(json[i].empName))
                        tr.append($('<td>').html(json[i].customerServiceName))
                        tr.append($('<td>').html(json[i].jiraId))
                        var look = $('<button>').attr("recordId", json[i].recordId).attr("empName", json[i].empName).attr("customerServiceName", json[i].customerServiceName).attr("jiraId", json[i].jiraId)
                            .addClass('btn btn-info lookDutyRecord').attr('data-toggle', 'modal').attr('data-target', '#lookModal').css('margin-right', '10px').html('<i class="glyphicon glyphicon-asterisk"></i>');
                        var upd = $('<button>').attr("id", json[i].id).attr("recordId", json[i].recordId).attr("empName", json[i].empName).attr("customerServiceName", json[i].customerServiceName).attr("jiraId", json[i].jiraId)
                            .addClass('btn btn-warning updbtn').css('margin-right', '10px').attr('data-toggle', 'modal').attr('data-target', '#updModal').html('<i class="glyphicon glyphicon-edit"></i>');
                        var td = $('<td>');
                        td.append(look).append(upd);
                        tr.append(td);
                        $("#tbody").append(tr);
                    }
                    if (data.code != "200") {
                        layer.msg('当前数据为空!');
                    }
                    //详情
                    $(".lookDutyRecord").unbind('click').click(function () {
                        $("#lookrecordId").val($(this).attr("recordId"));
                        $("#lookempName").val($(this).attr("empName"));
                        $("#lookcustomerServiceName").val($(this).attr("customerServiceName"));
                        $("#lookjiraId").val($(this).attr("jiraId"));
                    })
                    //修改
                    $(".updbtn").unbind('click').click(function () {
                        var id=$(this).attr("id");
                        $("#updrecordId").val($(this).attr("recordId"));
                        $("#updempName").val($(this).attr("empName"));
                        $("#updcustomerServiceName").val($(this).attr("customerServiceName"));
                        $("#updjiraId").val($(this).attr("jiraId"));
                        //提交
                        $("#updDutyRecord").unbind('click').click(function () {
                            layer.confirm('确认要修改吗？', function (index) {
                                $.ajax({
                                    url: '/updateDutyRecord',
                                    dataType: 'json',
                                    data: {
                                        id: id,
                                        remarkId: $("#updrecordId").val(),
                                        empName: $("#updempName").val(),
                                        customerServiceName:$("#updcustomerServiceName").val(),
                                        jiraId:$("#updjiraId").val()
                                    },
                                    success: function (data) {
                                        if (data.message == "修改成功") {
                                            setTimeout(function wlh() {
                                                window.location.href = "/dutyRecord"
                                            }, 500)
                                            layer.msg('已修改!', {
                                                icon: 1,
                                                time: 1000
                                            });
                                        } else if (data.message == "当前时间不在此月内,禁止修改") {
                                            layer.msg("当前时间不在此月内，禁止修改", {
                                                icon: 1,
                                                time: 1000
                                            });
                                        } else {
                                            layer.msg(data.result, {
                                                icon: 1,
                                                time: 1000
                                            });
                                        }
                                    }
                                })
                            })
                        })
                    })
                }
            })
        }

        //校验
        function checkAddInput() {
            if ($("#addDate").val().trim() == null || $("#addDate").val().trim() == '') {
                layer.msg("日期不能为空！");
                ajax().abort;
            }
            if ($("#addempName").val().trim()== null || $("#addempName").val().trim() == '') {
                layer.msg("值班人员不能为空！");
                ajax.abort;
            }
            if ($("#addcustomerServiceName").val().trim()== null || $("#addcustomerServiceName").val().trim() == '') {
                layer.msg("客服人员不能为空!");
                ajax.abort;
            }
            if ($("#addjiraId").val().trim()== null || $("#addjiraId").val().trim() == '') {
                layer.msg("jira编号不能为空!");
                ajax.abort;
            }
        }
    </script>
</head>

<body>
<div style="height: 10px;margin-left: 20px;"><b>当前操作:</b><span style="color: red">值班记录</span></div>
<input type="text" id="dutyRecoredDate" name="user_date" style="width:130px;margin-left: 10px;" class="layui-input" placeholder="请选择日期或Id"/>
<button id="query" style="margin: 30px;" class="btn btn-primary"><i class="glyphicon glyphicon-search"></i>&nbsp;查询</button>
<button class="btn btn-danger" data-toggle="modal" data-target="#addModal"><i class="glyphicon glyphicon-plus"></i>&nbsp;新增
</button>
<span style="float: right;margin:20px 40px 0px 0px;" id="username"></span>
<a id="home" href="/home" class="glyphicon glyphicon-home"></a>
<div>
    <table class="table table-bordered" id="table-bordered">
        <thead>
        <tr>
            <th>id</th>
            <th>值班人员</th>
            <th>客服人员</th>
            <th>jira编号</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="tbody">

        </tbody>
    </table>
</div>
<!--值班记录新增 -->
<div class="modal fade" id="addModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">值班记录 新增</h4>
            </div>
            <div class="modal-body">
                <table>
                    <tbody>
                    <tr>
                        <td style="width:12%;">值班日期:</td>
                        <td>
                            <input type="text" id="addDate"  class="form-control date form_datetime"  name="user_date" style="width:130px" placeholder="请选择日期"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">值班人员:</td>
                        <td style="width:60%;">
                            <select id="addempName" data-placeholder="请选择值班人员" multiple class="chzn-select"></select>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">客服人员:</td>
                        <td style="width:60%;">
                            <input class="form-control" id="addcustomerServiceName"></input>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">jira编号:</td>
                        <td style="width:60%;">
                            <input class="form-control" id="addjiraId"></input>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default">关闭</button>
                    <button id="addDutyRecord" class="btn btn-primary">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!--值班记录 详情-->
<div class="modal fade" id="lookModal" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">值班记录 详情</h4>
            </div>
            <div class="modal-body">
                <table>
                    <tbody>
                    <tr>
                        <td style="width:12%;">值班Id:</td>
                        <td>
                            <input  id="lookrecordId"  class="form-control" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">值班人员:</td>
                        <td style="width:60%;">
                            <input class="form-control" id="lookempName" readonly></input>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">客服人员:</td>
                        <td style="width:60%;">
                            <input class="form-control" id="lookcustomerServiceName" readonly></input>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">jira编号:</td>
                        <td style="width:60%;">
                            <input class="form-control" id="lookjiraId" readonly></input>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default">关闭</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!--值班记录修改 -->
<div class="modal fade" id="updModal" data-backdrop="static" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">值班记录 修改</h4>
            </div>
            <div class="modal-body">
                <table>
                    <tbody>
                    <tr>
                        <td style="width:12%;">值班Id:</td>
                        <td>
                            <input  id="updrecordId"  class="form-control date form_datetime" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">值班人员:</td>
                        <td style="width:60%;">
                            <select id="updempName" data-placeholder="请选择值班人员" multiple class="chzn-select"></select>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">客服人员:</td>
                        <td style="width:60%;">
                            <input class="form-control" id="updcustomerServiceName"></input>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">jira编号:</td>
                        <td style="width:60%;">
                            <input class="form-control" id="updjiraId"></input>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default">关闭</button>
                    <button id="updDutyRecord" class="btn btn-primary">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
