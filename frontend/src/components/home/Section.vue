<script setup>
import "@/assets/css/style.css"
</script>

<template>
    <section id="hero" class="d-flex align-items-center">
        <div class="container position-relative" data-aos="fade-up" data-aos-delay="100">
            <div class="row icon-boxes">
                <div class="col-md-6 col-lg-3 d-flex align-items-stretch mb-5 mb-lg-0" data-aos="zoom-in"
                    data-aos-delay="200">
                    <RouterLink to="/trip">
                        <div class="icon-box">
                            <div class="icon"> <img
                                    src="https://velog.velcdn.com/images/gangintheremark/post/2794bc8a-9055-4bec-9ec4-1c56ad74f4a4/image.png"
                                    class="thumbnail"></div>
                            <h4 class="title"><a href="">지역별 관광지</a></h4>
                            <p class="description">특정 지역의 핫플레이스를 알아보세요 🔥</p>
                        </div>
                    </RouterLink>
                </div>

                <div class="col-md-6 col-lg-3 d-flex align-items-stretch mb-5 mb-lg-0" data-aos="zoom-in"
                    data-aos-delay="300">
                    <RouterLink to="/trip">
                        <div class="icon-box">
                            <div class="icon">
                                <img src="https://velog.velcdn.com/images/gangintheremark/post/b4490e77-0b21-4429-87e0-cb5e2d42b8e4/image.png"
                                    class="thumbnail">
                            </div>
                            <h4 class="title"><a href="">카테고리별 관광지</a></h4>
                            <p class="description">음식점, 쇼핑, 숙박, 행사 등 다양한 카테고리별 관광지를 검색해보세요</p>
                        </div>
                    </RouterLink>
                </div>


                <div class="col-md-6 col-lg-3 d-flex align-items-stretch mb-5 mb-lg-0" data-aos="zoom-in"
                    data-aos-delay="400">
                    <RouterLink to="/review">
                        <div class="icon-box">
                            <div class="icon">
                                <img src="https://velog.velcdn.com/images/gangintheremark/post/d9aac910-a321-48cb-8870-11a3504857e7/image.png"
                                    class="thumbnail">
                            </div>
                            <h4 class="title"><a href="">여행리뷰</a></h4>
                            <p class="description">다양한 여행 후기를 보고 나에게 맞는 여행지를 찾아보세요 👀
                            </p>
                        </div>
                    </RouterLink>
                </div>


                <div class="col-md-6 col-lg-3 d-flex align-items-stretch mb-5 mb-lg-0" data-aos="zoom-in"
                    data-aos-delay="500">
                    <RouterLink to="/plan">
                        <div class="icon-box">
                            <div class="icon"><img
                                    src="https://velog.velcdn.com/images/gangintheremark/post/da0f34e6-a1c4-4c38-8235-80fc90a985e5/image.png"
                                    class="thumbnail"></div>
                            <h4 class="title"><a href="">나만의 여행코스 계획</a></h4>
                            <p class="description">나만의 여행 코스를 만들고 공유해보세요 🧳
                            </p>
                        </div>
                    </RouterLink>
                </div>

            </div>
        </div>
    </section>

</template>

<style scoped>
.thumbnail {
    position: inherit;
    width: 100%;
    height: 180px;
    border-radius: 20px;
}

.description {
    color: black;
}
</style>