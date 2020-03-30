var title;
  var idString = '';
	//接收一个值
	function oneValues(){
		var result;
		var url=window.location.search; //获取url中"?"符后的字串  
		if(url.indexOf("?")!=-1)
			result = url.substr(url.indexOf("=")+1);
		return result;
	}
	
	//接收多值
	function manyValues(){
		var url=window.location.search;
		if(url.indexOf("?")!=-1){
			var str = url.substr(1);
			strs = str.split("&"); 
			var key=new Array(strs.length);
			var value=new Array(strs.length);
			for(i=0;i<strs.length;i++){
				key[i]=strs[i].split("=")[0]
				value[i]=unescape(strs[i].split("=")[1]); 
				alert(key[i]+"="+value[i]);
			} 
		} 
	}
	// 格式化播放次数
	function formatPlayCount(count){
		if(count >= 100000)
			return (count/10000).toFixed(2) + "万";
		else
			return count;
	}
	// 格式化音乐时长
	function formatDuraton(time){
		if(time > -1){
			var temp;
			var sec = Math.floor(time/1000);
			//var min = Math.floor(time/60) % 60;
			var min = Math.floor(sec/60);
			sec = sec - min*60;
			if(min < 10){
				temp = "0" + min + ":";
			}else
				temp = min + ":";
			if(sec < 10){
				temp += "0" + sec ;
			}else
				temp += sec ;
		}
		return temp;
	};