<template>
    <div id="mainBody">
        <!-- HERO -->
        <b-row id="hero" class="text-light">
            <b-col cols="12" md="6" class="text-md-right text-center p-5">
                <h1 class="my-4 font-weight-bold">Simple Analytics</h1>
                <p class="w-100 w-md-75 float-center float-md-right">Our software was built to help you generate business analytics and future predictions. We even use the weather to check how the weather can effect your business and make even more accurate predictions.</p>
            </b-col>
            <b-col cols="12" md="6" class="p-5">
                <img class="w-100 w-md-75" src="https://s3.amazonaws.com/opal-marketing/website/images/week-view-2.png"> 
            </b-col>
        </b-row>
        
        <!-- FEATURES -->
        <b-row id="features" class="py-5">
            <b-col cols="12" class="text-center">
                <h1 class="pb-4 font-weight-bold">Features</h1>
                <b-col cols="10" md="8" offset="1" offset-md="2">
                    <b-row>
                        <b-col md="4" class="pb-4 py-md-0">
                            <i data-feather="trending-up" class="mb-4"></i>
                            <h2>Simple Business Analytics</h2>
                            <p>Our site tracks your businesses previous finacial records and uses them to show how your business has been preforming.</p>
                        </b-col>
                        <b-col md="4" class="pb-4 py-md-0">
                            <i data-feather="umbrella" class="mb-4"></i>
                            <h2>Accounts For The Weather</h2>
                            <p>We use the weather for the upcoming days to predict how busy business will be based on previous data from similar weather days.</p>
                        </b-col>
                        <b-col md="4" class="pb-4 py-md-0">
                            <i data-feather="map" class="mb-4"></i>
                            <h2>Compare Area Statistics</h2>
                            <p>We look at similar businesses within the area to get an even more accurate predicition for how your business will preform.</p>
                        </b-col>
                    </b-row>
                </b-col>
            </b-col>
        </b-row>

        <!-- PRICING -->
        <b-row id="pricing" class="py-5 bg-secondary text-light">
            <b-col cols="12">
                <h1 class="pb-4 text-center font-weight-bold">Pricing</h1>
                <b-col cols="10" md="8" offset="1" offset-md="2">
                    <b-row>
                        <b-col lg="4" class="pb-4 py-lg-0 d-flex align-items-stretch">
                            <b-card title="$50" sub-title="START-UP" class="text-dark">
                                <hr/>
                                <b-card-text class="flex-column">
                                    Comes with the basic functionality and predictions. Great for small and starting out businesses!
                                </b-card-text>
                                <b-button block variant="primary">Buy Now</b-button>
                            </b-card>
                        </b-col>
                        <b-col lg="4" class="pb-4 py-lg-0 d-flex align-items-stretch">
                            <b-card title="$100" sub-title="STANDARD" class="text-dark">
                                <hr/>
                                <b-card-text class="flex-column">
                                    Comes with the all the basic functionality and weather data tracking too. Perfect for companies looking to grow.
                                </b-card-text>
                                <b-button block variant="primary">Buy Now</b-button>
                            </b-card>
                        </b-col>
                        <b-col lg="4" class="pb-4 py-lg-0 d-flex align-items-stretch">
                            <b-card title="$125" sub-title="ULTIMATE" class="text-dark">
                                <hr/>
                                <b-card-text class="flex-column">
                                    The best we offer! All forms of data management and also includes integration using common POS systems.
                                </b-card-text>
                                <b-button block variant="primary">Buy Now</b-button>
                            </b-card>
                        </b-col>
                    </b-row>
                </b-col>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import feather from "feather-icons";

export default {
    mounted(){
        feather.replace({
            'stroke-width':2,
            'width':'100px',
            'height':'100px',
            'color':'#007bff'
        });
    }    
}
</script>
<style>
@media (min-width: 768px){ 
    .w-md-75{
        width:75% !important;
    }
}

#mainBody{
    min-height:calc(100vh - 85px - 70px);
}

*{
    font-family:'Roboto';
}
body{
    padding-top:85px;
}

.navbar{
    height: 85px;
    line-height: 42px;
}
.navbar-brand{
    font-size:32pt;
    font-weight: 600;
}
.nav-link{
    font-size:16pt;
}

#hero{
    min-height:50vh;
    background:linear-gradient(45deg, #0f7699, #ff7f9f)
}
#hero h1{
    font-size:40pt;
}
#hero p{
    font-size:20pt;
}

#features h1, #pricing h1{
    font-size:38pt;
}
#features p{
    font-size:14pt;
}

#pricing .card-title{
    font-size:50pt;
}

#footer{
    height:70px;
}

.rounded-full{
    border-radius:1000px;
}

</style>