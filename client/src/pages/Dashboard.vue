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
                        <b-tab title="Analytics">
                            <b-row>
                                <b-col cols="2" class="text-center">
                                    <b-dropdown :text="toggleText" variant="primary" size="lg" class="vertical-center" center>
                                       <b-dropdown-item-button @click="toggle('Weekly')">Weekly</b-dropdown-item-button>
                                       <b-dropdown-item-button @click="toggle('Yearly')">Yearly</b-dropdown-item-button>
                                    </b-dropdown>       
                                </b-col>
                                <b-col cols="8">
                                    <b-card-text id="graph"><plotly :data="graphData" :layout="graphLayout" :displayModeBar="true"/></b-card-text>
                                </b-col>
                            </b-row>
                        </b-tab>
                        <b-tab title="Weather" ><b-card-text>Test</b-card-text></b-tab>
                        <b-tab title="Settings"><b-card-text>Accuweather is great!</b-card-text></b-tab>
                    </b-tabs>
                </b-card>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import feather from "feather-icons";
import { Plotly } from 'vue-plotly';

export default {
    components: {
        Plotly
    },
    data(){
        return {
            weekly:[{
                x: ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
                y: [1, 2, 3, 2, 4, 6, 10],
                type:"scatter"
            }],
            yearly:[{
                x: ["Janurary","February","March","April","May","June","July","August","September","Octoeber","November","December"],
                y: [1, 0, 3, 2, 5, 4, 7, 6, 8, 10, 5, 0],
                type:"scatter"
            }],
            weeklyLayout:{
                title: "Weekly View",
                xaxis: {title: "Day of Week"},
                yaxis: {title: "Average Income"}
            },
            yearlyLayout:{
                title: "Yearly View",
                xaxis: {title: "Year of Month"},
                yaxis: {title: "Average Income"}
            },
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
            ],
            toggleText:'Weekly'
        };
    },
    methods: {
        toggle(toggle){
            this.toggleText = toggle;
        }        
    },
    computed: {
        graphData(){
            if(this.toggleText === 'Weekly'){
                return this.weekly;
            } else if(this.toggleText === 'Yearly'){
                return this.yearly;
            }
            return [];
        },
        graphLayout(){
            if(this.toggleText === 'Weekly'){
                return this.weeklyLayout;
            } else if(this.toggleText === 'Yearly'){
                return this.yearlyLayout;
            }
            return [];
        }
    },
    mounted(){
        feather.replace({
            'stroke-width':2
        });
    }    
}
</script>