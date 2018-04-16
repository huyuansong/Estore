<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript" src="/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript">
  		function checkData(){
  			var price = document.getElementsByName("price")[0].value;
  			if(isNaN(price)){
  				alert("单价必须是数字!");
  				document.getElementsByName("price")[0].value = "";
  				return false;
  			}else if(price<=0){
	  			alert("单价必须大于0!")
	  			document.getElementsByName("price")[0].value = "";
	  			return false;
  			}else{
  				return true;
  			}
  		}
  		
  		function subFunc(){
  			$("#bar1").css("display","block");
			 window.setInterval(ref, 10);
  		}
  		function ref(){
	  			$.post("/UploadMsgServlet",function(data){
	  				if(data!=null){
		  				var json = eval("("+data+")");
		  				$("#bar2").css("width",json.per+"%");
		  				$("#msg_div").html("<font size='1'>实时速度:"+json.speed+"KB/S,大致剩余时间:"+json.ltime+"S</font>");
	  				}
	  			});
  			}
  	</script>
  </head>
  <body style="text-align: center;">
  	<h1>Estore_添加商品</h1><hr>
  	<form action="/AddprodServlet" method="POST" enctype="multipart/form-data" onsubmit="return checkData()">
  		<table border="1">
  			<tr>
  				<td>商品名称</td>
  				<td><input type="text" name="name"/></td>
  			</tr>
  			<tr>
  				<td>单价</td>
  				<td><input type="text" name="price"/></td>
  			</tr>
  			<tr>
  				<td>商品种类</td>
  				<td>
					<select name="category">
						<option value="电子数码">电子数码</option>
						<option value="图书杂志">图书杂志</option>
						<option value="床上用品">床上用品</option>
						<option value="日用百货">日用百货</option>
						<option value="大型家电">大型家电</option>
						<option value="家用武器">家用武器</option>
					</select>
				</td>
  			</tr>
  			<tr>
  				<td>库存数量</td>
  				<td><input type="text" name="pnum"/></td>
  			</tr>
  			<tr>
  				<td>商品图片</td>
  				<td><input type="file" name="file1"/>
  				<div id="bar1" style="width: 200px;height: 20px; border: 1px solid green;; display:none;">
  					<div id="bar2" style="width: 0% ;height: 20px;background-color: green"/>
				</div>
				<div id="msg_div"></div>
  				</td>
  			</tr>
  			<tr>
  				<td>描述信息</td>
  				<td><textarea name="description" rows="6" cols="40"></textarea></td>
  			</tr>
  			<tr>
  				<td colspan="2"><input type="submit" value="添加商品" onclick="subFunc()"></td>
  			</tr>
  		</table>
  	</form>
  				
  </body>
</html>
