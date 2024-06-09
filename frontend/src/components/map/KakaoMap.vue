<template>
    <div id="map" ref="mapContainer"></div>
</template>

<script setup>
import { onMounted, ref, defineExpose, defineEmits } from 'vue'

const emit = defineEmits(['selected-place'])

const mapContainer = ref(null)
var map

onMounted(() => {
    loadScript();
})

const loadScript = () => {
    const key = import.meta.env.VITE_KAKAO_MAP_KEY
    const script = document.createElement('script')
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false`
    script.addEventListener('load', () => kakao.maps.load(initMap))
    document.head.appendChild(script)
}

const initMap = () => {
    const options = {
        center: new kakao.maps.LatLng(37.500613, 127.036431),
        level: 7
    }
    map = new kakao.maps.Map(mapContainer.value, options)
}

const displayMarkerByContent = (positions, markers) => {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers.length = 0;

    var imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/f43775aa-d206-423b-933b-615f374a8263/image.png";
    for (var i = 0; i < positions.length; i++) {
        var contentTypeId = positions[i].contentTypeId;
        switch (contentTypeId) {
            case 12:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/bf5d1a2c-f698-4364-8d9a-72f5c47881db/image.png";
                break;
            case 14:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/eecea87b-5d84-4dd7-ad63-270838fd1554/image.png";
                break;
            case 15:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/edba1b43-4668-4ab0-a71c-aad60b61e745/image.png";
                break;
            case 25:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/4ac49508-be8a-434b-a55a-2aa5128633fa/image.png";
                break;
            case 28:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/2291b92e-0c07-46d6-aedc-883e9e1a6a76/image.png";
                break;
            case 32:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/64b14158-cee1-459e-9fbd-1d320a053559/image.png";
                break;
            case 38:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/e479fdbd-b706-4d7d-9a4b-a68310155afd/image.png";
                break;
            case 39:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/973b2b92-734c-4f1a-9da2-a9354974709d/image.png";
                break;
        }

        var imageSize = new kakao.maps.Size(30, 30)
        var markerImage = new kakao.maps.MarkerImage(imgSrc, imageSize)

        var marker = new kakao.maps.Marker({
            map,
            position: positions[i].latlng,
            title: positions[i].title,
            image: markerImage,
        });
        markers.push(marker)
        map.setCenter(positions[0].latlng);
        map.setLevel(7)

        var content = `
    <div style="position: relative;">
        <button style="position: absolute; top: 5px; right: 5px; background: none; border: none; cursor: pointer; font-size: 20px;" onclick="window.closeOverlay(${i})">×</button>
        <div class="px-3 py-3 text-center" style="font-size: 14px; border-radius: 10px; background-color: white; border: 1px solid #ccc;">
            <img src=${positions[i].imgUrl} style="position: inherit; width: 180px;border-radius: 10px;" alt="이미지가 존재하지 않습니다.">
            <div class="fw-bold my-1" style="font-size:16px">${positions[i].title}</div>
            <div style="color: gray;">${positions[i].addr}</div>
            <button class="btn mt-1" onclick="window.addPlace(${i})">추가</button>
        </div>
    </div>
`;

        window.closeOverlay = (i) => {
            markers[i].customOverlay.setMap(null);
        }

        var customOverlay = new kakao.maps.CustomOverlay({
            position: positions[i].latlng,
            content: content,
            yAnchor: 1.1,
            zIndex: 3
        });

        marker.customOverlay = customOverlay;

        kakao.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                if (marker.customOverlay.getMap()) {
                    marker.customOverlay.setMap(null);
                } else {
                    marker.customOverlay.setMap(map);
                }


            };
        })(marker, i));

        window.addPlace = (index) => {
            emit('selected-place', positions[index]);
            markers[index].customOverlay.setMap(null);
        }
    }
}

const displayMarkerWithPolyline = (positions, markers, polylines, overlays) => {
    var totalDist = 0;
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers.length = 0;

    for (let i = 0; i < polylines.length; i++) {
        polylines[i].setMap(null);
    }
    polylines.length = 0;

    for (let i = 0; i < overlays.length; i++) {
        overlays[i].setMap(null);
    }
    overlays.length = 0;

    var imageSrc = "https://velog.velcdn.com/images/gangintheremark/post/f43775aa-d206-423b-933b-615f374a8263/image.png";

    for (var i = 0; i < positions.length; i++) {
        var imageSize = new kakao.maps.Size(30, 30);
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        var marker = new kakao.maps.Marker({
            map,
            position: positions[i].latlng,
            title: positions[i].title,
            image: markerImage
        });

        var content = `<span style="background-color: rgb(130, 203, 196);
    color: white;
    padding: 3px 8px;
    border-radius: 30px;"> ${i + 1}</span>`

        var indexOverlay = new kakao.maps.CustomOverlay({
            content,
            position: marker.getPosition(),
            yAnchor: 2.3,
            zIndex: 3
        });

        indexOverlay.setMap(map);
        overlays.push(indexOverlay);
        markers.push(marker);

        if (i < positions.length - 1) {
            var polyline = new kakao.maps.Polyline({
                path: [positions[i].latlng, positions[i + 1].latlng],
                strokeWeight: 3,
                strokeColor: '#FF0000',
                strokeOpacity: 0.7,
                strokeStyle: 'solid'
            });

            polyline.setMap(map);
            polylines.push(polyline);

            const distance = calculateDistance(positions[i].latlng, positions[i + 1].latlng).toFixed(2);
            totalDist += parseFloat(distance)
            var distanceContent = `<div style="color: black; background-color:white; border-radius:20px; padding: 2px 5px">
                ${distance} km
            </div>`;

            var midPointLat = (positions[i].latlng.getLat() + positions[i + 1].latlng.getLat()) / 2;
            var midPointLng = (positions[i].latlng.getLng() + positions[i + 1].latlng.getLng()) / 2;
            var midPoint = new kakao.maps.LatLng(midPointLat, midPointLng);

            var distanceOverlay = new kakao.maps.CustomOverlay({
                content: distanceContent,
                position: midPoint,
                yAnchor: 0.8,
                zIndex: 2
            });

            distanceOverlay.setMap(map);
            overlays.push(distanceOverlay);
        }
    }
    map.setCenter(positions[positions.length - 1].latlng);
    map.setLevel(7);

    return totalDist.toFixed(2);
}


const moveCenter = (lat, lng) => {
    map.setCenter(new kakao.maps.LatLng(lat, lng))
    map.setLevel(2)
}

const calculateDistance = (latlng1, latlng2) => {
    const R = 6371e3;
    const φ1 = latlng1.getLat() * Math.PI / 180;
    const φ2 = latlng2.getLat() * Math.PI / 180;
    const Δφ = (latlng2.getLat() - latlng1.getLat()) * Math.PI / 180;
    const Δλ = (latlng2.getLng() - latlng1.getLng()) * Math.PI / 180;

    const a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
        Math.cos(φ1) * Math.cos(φ2) *
        Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    const distance = R * c;
    return distance / 1000;
};

defineExpose({
    moveCenter,
    displayMarkerByContent,
    displayMarkerWithPolyline
})
</script>

<style scoped></style>