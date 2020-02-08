<template>
    <div id="mainBody">
        <b-row>
            <b-col cols="12" class="py-4">
                <b-card no-body class="">
                    <b-tabs pills card vertical>
                        <!-- TAB ONE -->
                        <b-tab title="Weekly View" active><b-card-text>
                            <b-row class="pb-4">
                                <b-col cols="5" >
                                    <b-button variant="primary" class="float-right">
                                        <i class="sideArrows" data-feather="arrow-left"></i>
                                    </b-button>
                                </b-col>
                                <b-col cols="2" class="text-center">
                                    <p class="vertical-center">{{ days[0].date }} - {{ days[6].date }}</p>
                                </b-col>
                                <b-col cols="5">
                                    <b-button variant="primary" class="vertical-center">
                                        <i class="sideArrows" data-feather="arrow-right"></i>
                                    </b-button>
                                </b-col>
                            </b-row>
                            <b-row>    
                                <b-col v-for="day in days" :key="day.date" class="text-center">
                                    <h5 class="font-weight-bold">{{ day.day }}</h5>
                                    <h6>{{ day.date }}</h6>
                                    <hr/>
                                    <h5>Weather</h5>
                                    <i color="#000" :data-feather="day.weather" class="weatherIcon"></i>
                                    <h6>{{ day.tempLow }}&deg;/ {{ day.tempHigh }} &deg;</h6>
                                    <hr/>
                                    <p>Predicted: <br/> ${{ day.predicted }} </p>
                                    <p>Actual: <br/> ${{ day.actual }} </p>
                                </b-col>
                            </b-row>
                        </b-card-text></b-tab>
                        <b-tab title="Analytics"><b-card-text>Tab contents 2</b-card-text></b-tab>
                        <b-tab title="Weather"><b-card-text>Tab contents 3</b-card-text></b-tab>
                        <b-tab title="Settings"><b-card-text>Accuweather is great!</b-card-text></b-tab>
                    </b-tabs>
                </b-card>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import feather from "feather-icons";

export default {
    data(){
        return {
            days:[
                {
                    day: "Sunday",
                    date: "2/8",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Monday",
                    date: "2/9",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Tuesday",
                    date: "2/10",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Wednesday",
                    date: "2/11",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Thursday",
                    date: "2/12",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Friday",
                    date: "2/13",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Saturday",
                    date: "2/14",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                }
            ]
        };
    },
    mounted(){
        feather.replace({
            'stroke-width':2
        });
    }    
}
</script>