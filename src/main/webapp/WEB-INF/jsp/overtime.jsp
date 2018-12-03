<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/table.css" rel="stylesheet">
    <script src="http://libs.baidu.com/jquery/2.0.1/jquery.min.js"></script>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/layer/layer.js"></script>
    <script type="text/javascript" src="/laydate/laydate.js"></script>
    <script type="text/javascript" src="/js/Date.js"></script>

    <script>
        $(function() {
            laydate.render({
                elem: '#startDate'
            });
            laydate.render({
                elem: '#endDate'
            });
            laydate.render({
                elem: '#selectDate'
            });

            query();
        })

        function dailyRecord() {
            window.location.href="/table";
        }

        function query(){
            $("#tbody").empty();
            var startDate = $('#startDate').val().replace('-', '').replace('-', '');
            var endDate = $('#endDate').val().replace('-', '').replace('-', '');
            var userId = $('#userId').val();
            if (startDate != '' && endDate != '') {
                if (parseInt(startDate) > parseInt(endDate)) {
                    layer.msg('结束日期不能比开始日期早')
                    return;
                }
            }
            if (startDate == '' && endDate != '') {
                layer.msg('结束日期不为空时,开始日期也不能为空')
                return;
            }
            $.ajax({
                type: 'get',
                url: '/getOvertime',
                dataType: 'json',
                data: {
                    startTime:startDate,
                    endTime:endDate,
                    userId:userId
                },
                success: function (data) {
                    for (var i in  data.data) {
                        var tr = $('<tr>');
                        if (typeof (data.data[i].id) != 'undefined') {
                            tr.append($('<td>').html(data.data[i].id))
                            tr.append($('<td>').html(data.data[i].date))
                            tr.append($('<td>').html(data.data[i].start_time))
                            tr.append($('<td>').html(data.data[i].end_time))
                            tr.append($('<td>').html(data.data[i].duration))
                            tr.append($('<td>').html(data.data[i].full_name))
                            tr.append($('<td>').html(data.data[i].cause))
                            tr.append($('<td>').html(data.data[i].matter))
                            tr.append($('<td>').html(data.data[i].schedule))
                            tr.append($('<td>').html(data.data[i].result))
                            tr.append($('<td>').html(data.data[i].remark))
                            var set = $('<button>').addClass('btn btn-warning updbtn').css('margin-right', '10px').attr('data-toggle', 'modal').attr('data-target', '#setModal').html('<i class="glyphicon glyphicon-edit"></i>');
                            var del = $('<button>').addClass('btn btn-danger delbtn').html('<i class="glyphicon glyphicon-trash"></i>');
                            var td = $('<td>');
                            td.append(set);
                            td.append(del);
                            tr.append(td);
                            $("#tbody").append(tr);
                        }
                    }
                },
            })
        }

    </script>

</head>
<body>
<input type="text" id="startDate" name="user_date" style="width:130px;margin-left: 10px;" class="layui-input" placeholder="请选择开始日期" /> —
<input type="text" id="endDate" name="user_date" style="width:130px" class="layui-input" placeholder="请选择结束日期" />
<input id="userId" placeholder="请输入用户ID" />
<button id="query" style="margin: 30px;" class="btn btn-primary" onclick="query()"><i class="glyphicon glyphicon-search"></i>&nbsp;查询</button>
<button class="btn btn-danger" data-toggle="modal" data-target="#addModal"><i class="glyphicon glyphicon-plus"></i>&nbsp;新增</button>
<button class="btn btn-danger" onclick="dailyRecord()"><i class="glyphicon glyphicon-plus"></i>&nbsp;日报</button>
<span style="float: right;margin:20px 40px 0px 0px;" id="username"></span>
<div>
    <table class="table table-bordered" id="table-bordered">
        <thead>
        <tr>
            <th width="50px">序号
            <th width="75px">日期
            </th>
            <th width="75px">开始时间
            </th>
            <th width="70px">结束时间
            </th>
            <th width="60px">时长
            </th>
            <th width="50px">姓名
            </th>
            <th width="50px">事由
            </th>
            <th width="105px">事项
            </th>
            <th width="75px">处理进度
            </th>
            <th width="120px">处理结果
            </th>
            <th width="120px">备注</th>
            <th width="120px">操作</th>
        </tr>
        </thead>
        <tbody id="tbody">

        </tbody>
    </table>
</div>
    </table>
</div>

<!--新增加班记录 -->
<div class="modal fade" id="addModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">新增</h4>
            </div>
            <div class="modal-body">
                <table>
                    <tbody>
                    <tr>
                        <td style="width:12%;">日期:</td>
                        <td>
                            <input type="text" id="selectDate" name="date" style="width:130px" class="layui-input form-control" placeholder="请选择开始时间" />
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">开始时间 :
                        </td>
                        <td style="width:60%;">
                            <input type="text" class="form-control" id="startTime">
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">结束时间 :
                        </td>
                        <td style="width:60%;">
                            <input type="text" class="form-control" id="endTime">
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">时长 :
                        </td>
                        <td style="width:60%;">
                            <input type="text" class="form-control" id="duration">
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">事由 :
                        </td>
                        <td style="width:60%;">
                            <textarea class="form-control" id="cause"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">事项 :
                        </td>
                        <td style="width:60%;">
                            <textarea class="form-control" id="matter"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">处理进度 :
                        </td>
                        <td style="width:60%;">
                            <textarea class="form-control" id="schedule"></textarea>;
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">处理结果 :
                        </td>
                        <td style="width:60%;">
                            <textarea class="form-control" id="result"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:12%;">备注:</td>
                        <td style="width:60%;">
                            <textarea class="form-control" id="remark"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default">关闭</button>
                    <button id="add" class="btn btn-primary">提交</button>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

</body>
</html>
