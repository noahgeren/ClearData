<template>
    <div id="mainBody">
        <b-row>
            <b-col cols="12" class="py-4">
                <b-card no-body class="">
                    <b-tabs pills card vertical>
                        <!-- TAB ONE -->
                        <!-- <b-tab title="Weekly View" active ><b-card-text>Test</b-card-text></b-tab>-->
                        <b-tab title="Analytics" active>
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
                            <!-- Numbahs -->
                            <b-row id="numbers" class="py-5 bg-secondary text-light">
                                <b-col cols="12">
                                    <h1 class="pb-4 text-center font-weight-bold">Numbers</h1>
                                    <b-col cols="10" md="8" offset="1" offset-md="2">
                                        <b-row>
                                            <b-col lg="4" class="pb-4 py-lg-0 d-flex align-items-stretch">
                                                <b-card title="Historical" class="text-dark">
                                                    <hr/>
                                                    <b-card-text class="flex-column">
                                                        Hisorical numbers go here qwerqwerqerqw {{weekly[0].average}}
                                                    </b-card-text>
                                                    
                                                </b-card>
                                            </b-col>
                                            <b-col lg="4" class="pb-4 py-lg-0 d-flex align-items-stretch">
                                                <b-card title="Current"  class="text-dark">
                                                    <hr/>
                                                    <b-card-text class="flex-column">
                                                        Current numbers go here qwerqw erqwefqfre {{weekly2.average}}
                                                    </b-card-text>
                                                    
                                                </b-card>
                                            </b-col>
                                            <b-col lg="4" class="pb-4 py-lg-0 d-flex align-items-stretch">
                                                <b-card title="Projected"  class="text-dark">
                                                    <hr/>
                                                    <b-card-text class="flex-column">
                                                        Projected numbers go here werwerwerwerqwerqwer {{weekly3.average}}
                                                    </b-card-text>
                                                    
                                                </b-card>
                                            </b-col>
                                        </b-row>
                                    </b-col>
                                </b-col>
                            </b-row>
                        </b-tab>
                        
                        <b-tab title="Weather" ><b-card-text>
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
import axios from 'axios';



export default {
    components: {
        Plotly
    },
    data(){
        return {
            
            weekly:[{ // historical
                x: ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
                y: [12, 21, 12, 33, 12, 16, 24],
                type:"scatter",
                name: "Historical Avgs",
                average: 123
            }],

            weekly2:{ // projected
                x: ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
                y: [10, 20, 30, 20, 40, 60, 20],
                type:"scatter",
                name: "Projected Avgs",
                average:123
            },

            weekly3:{ // this week so far
                x: ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
                y: [14, 23, 22, 16],
                type:"scatter",
                name: "This Week",
                average: 123
            },
            
            yearly:[{ // historical
                x: ["Janurary","February","March","April","May","June","July","August","September","Octoeber","November","December"],
                y: [15, 10, 33, 23, 52, 45, 76, 62, 81, 10, 52, 50],
                type:"scatter",
                name: "Historical Avgs"
            }],
            yearly2:{ // projected
                x: ["Janurary","February","March","April","May","June","July","August","September","Octoeber","November","December"],
                y: [10, 0, 30, 20, 50, 40, 70, 60, 80, 100, 50, 0],
                type:"scatter",
                name: "Projected Avgs",
                
            },
            yearly3:{ // this year so far
                x: ["Janurary","February","March","April","May","June","July","August","September","Octoeber","November","December"],
                y: [10, 0, 30, 20, 50, 40, 70, 60, 80, 100, 50, 0],
                type:"scatter",
                name: "This Year",
                
            },

            weeklyLayout:{
                title: "Weekly View",
                xaxis: {title: "Day of Week"},
                yaxis: {title: "Average Income"},
                showlegend: true,
                 transition: {
                    duration: 500,
                    easing: 'cubic-in-out'
                    },
                    frame: {
                    duration: 500
                    }
            },
            yearlyLayout:{
                title: "Yearly View",
                xaxis: {title: "Year of Month"},
                yaxis: {title: "Average Income"},
                showlegend: true,
                 transition: {
                    duration: 500,
                    easing: 'cubic-in-out'
                    },
                    frame: {
                    duration: 500
                    }
            },
            days:[
                {
                    day: "Sunday",
                    date: "2/9",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Monday",
                    date: "2/10",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Tuesday",
                    date: "2/11",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Wednesday",
                    date: "2/12",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Thursday",
                    date: "2/13",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Friday",
                    date: "2/14",
                    weather: "cloud-drizzle",
                    tempLow: "26",
                    tempHigh: "42",
                    predicted: "420.69",
                    actual: "400.00"
                },
                {
                    day: "Saturday",
                    date: "2/15",
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
        },
    },
    mounted(){
        
       const week1avg = '/api/averages/week/1';
       const week2avg = '/api/averages/week/2';
       const week3avg = '/api/averages/week/3';

       const year1avg = '/api/averages/year/0';
       const year2avg = '/api/averages/year/1';
       const year3avg = '/api/averages/year/2';

            
        axios
        .all([axios.get(week1avg), axios.get(week2avg), axios.get(week3avg), axios.get(year1avg), axios.get(year2avg), axios.get(year3avg)])
        .then(axios.spread((...responses) => {

            this.weekly[0].y = responses[0].data;
            this.weekly2.y = responses[1].data;
            this.weekly3.y = responses[2].data;

            this.yearly[0].y = responses[3].data;
            this.yearly2.y = responses[4].data;
            this.yearly3.y = responses[5].data;



            
        })) 
        .catch(error => {
            console.log(error)
        })
        .finally(() => {this.loading = false;});
        

        this.weekly.push(this.weekly2);
        this.weekly.push(this.weekly3);
        this.yearly.push(this.yearly2);
        this.yearly.push(this.yearly3);

        feather.replace({
            'stroke-width':2
        });
    }    
}
</script>