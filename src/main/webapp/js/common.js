//---------------------
// 发送数据给后端统一处理框架
// <a class="Form_Data_Submit" data="id:1020;name:talent" href="/itemReward/agree"></a>
//---------------------
$(function(){
	$(".Form_Data_Submit").each(function(i, element){
		$(element).on("click", function(event){
			event.preventDefault();
			var target = $(event.currentTarget);
			var url = target.attr("href"), data = target.attr("data");
			var dataToServer = {};
			data = data.split(";");
			for (var i = 0; i < data.length; i++) {
				var item = data[i].split(":");
				var key = item.shift(), value = item.join(":");
				dataToServer[key] = value;
			};
			$.post(url, dataToServer, function(response){
				var res = $.parseJSON(response);
				if(res.errCode == 0){
					//成功
					//判断是否跳转
					if(res.redirect){
						window.location.href = res.redirect;
					}else{
						window.location.reload();
					}
				}
				//打印全局消息
				if(res.errMsg){
					top.window.receiveMessage(res.errMsg);
				}
			});
		});

	});
});