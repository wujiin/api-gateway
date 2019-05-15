<!DOCTYPE html>
<html lang="zh-cmn-Hans">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>地址导航</title>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=NKUpBYCit9mv78eBOEZnlG2R6MC7GeUy"></script>  
    <script type="text/javascript" src="/static/assets/js/jquery-2.1.0.min.js"></script>
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style> 
 
</head>
 
<body>
<input type="hidden" value=${house.lon} id="lon"/>
<input type="hidden" value=${house.lat} id="lat"/>
<input type="hidden" value=${house.cityCode} id="cityCode"/>
   <div id="allmap"></div>
</body>  
</html>  
<script type="text/javascript">
    const lon = $('#lon').val();
    const lat = $('#lat').val();
    const cityCode =$('#cityCode').val();

    var map = new BMap.Map("allmap");
    var point = new BMap.Point(lon,lat);
    map.centerAndZoom(point, 16);
    map.enableScrollWheelZoom();

    var marker=new BMap.Marker(point);
    map.addOverlay(marker);

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            //map.panTo(r.point);//地图中心点移到当前位置
            var latCurrent = r.point.lat;
            var lngCurrent = r.point.lng;
            location.href="http://api.map.baidu.com/direction?origin="+latCurrent+","+lngCurrent+"&destination="+lat+","+lon+"&mode=driving&region="+cityCode+"&output=html";
        }
        else {
            alert('failed'+this.getStatus());
        }        
    },{enableHighAccuracy: true})
    map.addOverlay(marker);
</script>