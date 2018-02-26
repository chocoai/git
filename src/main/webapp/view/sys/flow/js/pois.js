		function calcPoints(poiObj1,poiObj2,dis){
			var pois = [];
			var ord1 = poiObj1.ord;
			var l1 = poiObj1.left;
			var t1 = poiObj1.top;
			var w1 = poiObj1.width;
			var h1 = poiObj1.height;
			
			var ord2 = poiObj2.ord;
			var l2 = poiObj2.left;
			var t2 = poiObj2.top;
			var w2 = poiObj2.width;
			var h2 = poiObj2.height;
			
			var boundx1=[l1, l1 + w1, l1 - dis, l1 + w1 + dis];
			var boundy1=[t1, t1 + h1, t1 - dis, t1 + h1 + dis];
			var boundx2=[l2, l2 + w2, l2 - dis, l2 + w2 + dis];
			var boundy2=[t2, t2 + h2, t2 - dis, t2 + h2 + dis];
			
			if(ord1=="n" && ord2=="n"){
				pois = calcPointsNN(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="n" && ord2=="e"){
				pois = calcPointsNE(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="e" && ord2=="n"){
				pois = calcPointsNE(l2,t2,w2,h2,l1,t1,w1,h1,boundx2,boundy2,boundx1,boundy1);
				pois.reverse();
			}
			else if(ord1=="n" && ord2=="s"){
				pois = calcPointsNS(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="s" && ord2=="n"){
				pois = calcPointsNS(l2,t2,w2,h2,l1,t1,w1,h1,boundx2,boundy2,boundx1,boundy1);
				pois.reverse();
			}
			else if(ord1=="n" && ord2=="w"){
				pois = calcPointsNW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="w" && ord2=="n"){
				pois = calcPointsNW(l2,t2,w2,h2,l1,t1,w1,h1,boundx2,boundy2,boundx1,boundy1);
				pois.reverse();
			}
			else if(ord1=="e" && ord2=="e"){
				pois = calcPointsEE(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="e" && ord2=="s"){
				pois = calcPointsES(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="s" && ord2=="e"){
				pois = calcPointsES(l2,t2,w2,h2,l1,t1,w1,h1,boundx2,boundy2,boundx1,boundy1);
				pois.reverse();
			}
			else if(ord1=="e" && ord2=="w"){
				pois = calcPointsEW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="w" && ord2=="e"){
				pois = calcPointsEW(l2,t2,w2,h2,l1,t1,w1,h1,boundx2,boundy2,boundx1,boundy1);
				pois.reverse();
			}
			else if(ord1=="s" && ord2=="s"){
				pois = calcPointsSS(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="s" && ord2=="w"){
				pois = calcPointsSW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			else if(ord1=="w" && ord2=="s"){
				pois = calcPointsSW(l2,t2,w2,h2,l1,t1,w1,h1,boundx2,boundy2,boundx1,boundy1);
				pois.reverse();
			}
			else if(ord1=="w" && ord2=="w"){
				pois = calcPointsWW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2);
			}
			
			return pois;
		}
		
		function calcPointsNN(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + (w1 / 2);
			py= t1;
			pois.push([px,py]);
			py = boundy1[2];
			
			px2 = l2 + (w2 / 2);
			py2 = t2;
			
			if(py < boundy2[2]){
				pois.push([px,py]);
				
				if(boundy2[2] >= boundy1[3] && px2 > boundx1[2] && px2 < boundx1[3]){
					px = px2 >= px ? boundx1[3] : boundx1[2];
					pois.push([px,py]);
					
					py = boundy2[2];
					pois.push([px,py]);
					
					pois.push([px2,boundy2[2]]);
				}
				else{
					px = px2;
					pois.push([px,py]);
				}
			}
			else if(py > boundy2[2]){
				if(py >= boundy2[3] && px > boundx2[2] && px < boundx2[3]){
					pois.push([px,py]);
					px = px >= px2 ? boundx2[3] : boundx2[2];
					pois.push([px,py]);
					
					py = boundy2[2];
					pois.push([px,py]);
					
					pois.push([px2,boundy2[2]]);
				}
				else{
					py = boundy2[2];
					pois.push([px,py]);
					
					pois.push([px2,boundy2[2]]);
				}
			}
			else{
				pois.push([px,py]);
				pois.push([px2,boundy2[2]]);
			}
			pois.push([px2,py2]);
			return pois;
		}
		
		function calcPointsNE(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + (w1 / 2);
			py= t1;
			pois.push([px,py]);
			
			py = boundy1[2];
			
			px2 = l2 + w2;
			py2= t2 + (h2 / 2);
			
			if(boundx2[3] <= px && py2 <= py){
				py = py2;
				pois.push([px,py]);
			}
			else if(px <= boundx2[0] && py > boundy2[2] && py < boundy2[3]){
				py = boundy2[2];
				pois.push([px,py]);
				
				px = boundx2[3];
				pois.push([px,py]);
				
				py = py2;
				pois.push([px,py]);
			}
			else if(py2 >= boundy1[1] && boundx2[3] > boundx1[2] && boundx2[3] < boundx1[3]){
				pois.push([px,py]);
				px=boundx1[3];
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			else{
				pois.push([px,py]);
				px = boundx2[3];
				pois.push([px,py]);
				py = py2;
				pois.push([px,py]);
			}
			
			pois.push([px2,py2]);
			return pois;	
		}
		
		function calcPointsNS(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + (w1 / 2);
			py= t1;
			pois.push([px,py]);
			
			py = boundy1[2];
			
			px2 = l2 + (w2/2);
			py2= t2 + h2;
			
			var pLeft = boundx1[2] < boundx2[2] ? boundx1[2] : boundx2[2];
			var pRight = boundx1[3] > boundx2[3] ? boundx1[3] : boundx2[3];
				
			if(py >= boundy2[2]){
				pois.push([px,py]);
				px=px2;
				pois.push([px,py]);
			}
			else if(boundx1[1] <= boundx2[2]){
				pois.push([px,py]);
				px=boundx2[2];
				pois.push([px,py]);
				py=boundy2[3];
				pois.push([px,py]);
				px=px2;
				pois.push([px,py]);
			}
			else if(boundx1[0] >= boundx2[3]){
				pois.push([px,py]);
				px=boundx2[3];
				pois.push([px,py]);
				py=boundy2[3];
				pois.push([px,py]);
				px=px2;
				pois.push([px,py]);
			}
			else{
				pois.push([px,py]);
				px = pRight;
				pois.push([px,py]);
				py = boundy2[3];
				pois.push([px,py]);
				px=px2;
				pois.push([px,py]);
			}
			pois.push([px2,py2]);
			return pois;	
		}
		
		function calcPointsNW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + (w1 / 2);
			py= t1;
			pois.push([px,py]);
			
			py = boundy1[2];
			
			px2 = l2;
			py2= t2 + (h2 / 2);
			
			if(boundx2[2] >= px && py2 <= py){
				py = py2;
				pois.push([px,py]);
			}
			else if(px >= boundx2[1] && py > boundy2[2] && py < boundy2[3]){
				py = boundy2[2];
				pois.push([px,py]);
				
				px = boundx2[2];
				pois.push([px,py]);
				
				py = py2;
				pois.push([px,py]);
			}
			else if(py2 >= boundy1[1] && boundx2[2] > boundx1[2] && boundx2[2] < boundx1[3]){
				pois.push([px,py]);
				px=boundx1[2];
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			else{
				pois.push([px,py]);
				px = boundx2[2];
				pois.push([px,py]);
				py = py2;
				pois.push([px,py]);
			}
			pois.push([px2,py2]);
			return pois;
		}
		
		function calcPointsEE(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + w1;
			py= t1 + (h1 / 2);
			pois.push([px,py]);
			
			px = boundx1[3];
			
			px2 = l2 + w2;
			py2= t2 + (h2 / 2);
			
			if(px < boundx2[3]){
				if(boundx1[3] <= boundx2[2] && py > boundy2[2] && py < boundy2[3]){
					pois.push([px,py]);
					py = py >= py2 ? boundy2[3] : boundy2[2];
					pois.push([px,py]);
					px=boundx2[3];
					pois.push([px,py]);
					py = py2;
					pois.push([px,py]);
				}
				else{
					px=boundx2[3];
					pois.push([px,py]);
					py=py2;
					pois.push([px,py]);
				}
			}
			else if(px > boundx2[3]){
				pois.push([px,py]);
				
				if(boundx1[2] >= boundx2[3] && py2 > boundy1[2] && py2 < boundy1[3]){
					py = py2 >= py ? boundy1[3] : boundy1[2];
					pois.push([px,py]);
					px=boundx2[3];
					pois.push([px,py]);
					py = py2;
					pois.push([px,py]);
				}
				else{
					py=py2;
					pois.push([px,py]);
				}
			}
			else{
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			
			pois.push([px2,py2]);
			return pois;
		}
		
		function calcPointsES(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + w1;
			py= t1 + (h1 / 2);
			pois.push([px,py]);
			
			px = boundx1[3];
			
			px2 = l2 + (w2 / 2);
			py2= t2 + h2;
			
			if(boundx1[1] <= px2 && py >= boundy2[1]){
				px=px2;
				pois.push([px,py]);
			}
			else if(px2 <= boundx1[0] && boundy2[3] > boundy1[2] && boundy2[3] < boundy1[3] ){
				pois.push([px,py]);
				py = boundy1[3];
				pois.push([px,py]);
				px = px2;
				pois.push([px,py]);
			}
			else if(boundy1[3] >= boundy2[0] && boundx1[1] > boundx2[2] && boundx1[1] < boundx2[3]){
				pois.push([px,py]);
				px=boundx2[3];
				pois.push([px,py]);
				py=boundy2[3];
				pois.push([px,py]);
				px=px2;
				pois.push([px,py]);
			}
			else{
				pois.push([px,py]);
				py=boundy2[3];
				pois.push([px,py]);
				px=px2;
				pois.push([px,py]);
			}
			pois.push([px2,py2]);
			return pois;
		}
		
		function calcPointsEW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + w1;
			py= t1 + (h1 / 2);
			pois.push([px,py]);
			
			px = boundx1[3];
			
			px2 = l2;
			py2= t2 + (h2 / 2);
			
			var pTop = boundy1[2] < boundy2[2] ? boundy1[2] : boundy2[2];
			var pBottom = boundy1[3] > boundy2[3] ? boundy1[3] : boundy2[3];
				
			if(px <= boundx2[2]){
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			else if(boundy1[3] <= boundy2[2]){
				pois.push([px,py]);
				py=boundy2[2];
				pois.push([px,py]);
				px=boundx2[2];
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			else if(boundy1[2] >= boundy2[3]){
				pois.push([px,py]);
				py=boundy2[3];
				pois.push([px,py]);
				px=boundx2[2];
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			else{
				pois.push([px,py]);
				py = pBottom;
				pois.push([px,py]);
				px = boundx2[2];
				pois.push([px,py]);
				py = py2;
				pois.push([px,py]);
			}
			pois.push([px2,py2]);
			return pois;
		}
		
		function calcPointsSS(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + (w1 / 2);
			py= t1 + h1;
			pois.push([px,py]);
			
			py = boundy1[3];
			
			px2 = l2 + (w2 / 2);
			py2= t2 + h2;
			
			if(py < boundy2[3]){
				if(boundy1[3] <= boundy2[2] && px > boundx2[2] && px < boundx2[3]){
					pois.push([px,py]);
					px = px2 > px ? boundx2[2] : boundx2[3];
					pois.push([px,py]);
					py=boundy2[3];
					pois.push([px,py]);
					px=px2;
					pois.push([px,py]);
				}
				else{
					py=boundy2[3];
					pois.push([px,py]);
					px=px2;
					pois.push([px,py]);
				}
			}
			else if(py > boundy2[3]){
				pois.push([px,py]);
				if(boundy1[2] >= boundy2[3] && px2 > boundx1[2] && px2 < boundx1[3]){
					px = px2 > px ? boundx1[3] : boundx1[2];
					pois.push([px,py]);
					py=boundy2[3];
					pois.push([px,py]);
					px=px2;
					pois.push([px,py]);
				}
				else{
					px=px2;
					pois.push([px,py]);
				}
			}
			else{
				pois.push([px,py]);
				px=px2;
				pois.push([px,py]);
			}
			pois.push([px2,py2]);
			return pois;
		}
		
		function calcPointsSW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1 + (w1 / 2);
			py= t1 + h1;
			pois.push([px,py]);
			
			py = boundy1[3];
			
			px2 = l2;
			py2 = t2 + (h2 / 2);
			
			if(py <= py2 && px <= boundx2[2]){
				py = py2;
				pois.push([px,py]);
			}
			else if(px >= boundx2[1] && py > boundy2[2] && py2 < boundy2[3]){
				py = boundy2[3];
				pois.push([px,py]);
				px = boundx2[2];
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			else if(py2 <= boundx1[0] && boundx2[2] > boundx1[2] && boundx2[2] < boundx1[3]){
				pois.push([px,py]);
				px=boundx1[2];
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			else{
				pois.push([px,py]);
				px=boundx2[2];
				pois.push([px,py]);
				py = py2;
				pois.push([px,py]);
			}
			pois.push([px2,py2]);
			return pois;
		}
		
		function calcPointsWW(l1,t1,w1,h1,l2,t2,w2,h2,boundx1,boundy1,boundx2,boundy2){
			var pois = [];
			var px,py,px2,py2;
			
			px = l1;
			py= t1 + (h1 / 2);
			pois.push([px,py]);
			
			px = boundx1[2];
			
			px2 = l2;
			py2 = t2 + (h2 / 2);
			
			if(px < boundx2[2]){
				pois.push([px,py]);
				if(boundx2[2] >= boundx1[1] && py2 > boundy1[2] && py2 < boundy1[3]){
					py = py2 > py ? boundy1[3] : boundy1[2];
					pois.push([px,py]);
					px=boundx2[2];
					pois.push([px,py]);
					py=py2;
					pois.push([px,py]);
				}
				else{
					py=py2;
					pois.push([px,py]);
				}
			}
			else if(px > boundx2[2]){
				if(px >= boundx2[1] && py > boundy2[2] && py < boundy2[3]){
					pois.push([px,py]);
					py = py2 > py ? boundy2[2] : boundy2[3];
					pois.push([px,py]);
					px=boundx2[2];
					pois.push([px,py]);
					py=py2;
					pois.push([px,py]);
				}
				else{
					px=boundx2[2];
					pois.push([px,py]);
					py=py2;
					pois.push([px,py]);
				}
			}
			else{
				pois.push([px,py]);
				py=py2;
				pois.push([px,py]);
			}
			
			pois.push([px2,py2]);
			return pois;
		}