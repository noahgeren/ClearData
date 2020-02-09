<template>
    <div id="mainBody">
        <b-row>
            <b-col cols="12" class="py-4">
                <b-card no-body class="">
                    <b-tabs pills card vertical>
                        <b-tab title="Analytics" active>
                            <b-row id="numbers" class="py-5 mb-4">
                                <b-col cols="12">
                                    <h1 class="pb-4 text-center font-weight-bold">Analytics</h1>
                                    <b-col cols="10" md="8" offset="1" offset-md="2">
                                        <b-row>
                                            <b-col class="text-center">
                                                <h4>Daily Average Income</h4>
                                                <br/>
                                                <h1>${{ dailyStats[0] }}</h1>
                                            </b-col>
                                            <b-col class="text-center">
                                                <h4>Average Spent Per Customer</h4>
                                                <br/>
                                                <h1>${{ dailyStats[1] }}</h1>
                                            </b-col>
                                            <b-col class="text-center">
                                                <h4>Current Weekly Total</h4>
                                                <br/>
                                                <h1>${{ dailyStats[2] }}</h1>
                                            </b-col>
                                        </b-row>
                                    </b-col>
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col cols="10" offset="1">
                                    <b-dropdown :text="toggleText" variant="primary" size="lg" class="vertical-center" center style="z-index:100;">
                                       <b-dropdown-item-button @click="toggle('Weekly')">Weekly</b-dropdown-item-button>
                                       <b-dropdown-item-button @click="toggle('Yearly')">Yearly</b-dropdown-item-button>
                                    </b-dropdown>       
                                </b-col>
                                <b-col cols="10" offset="1">
                                    <b-card-text id="graph"><plotly :data="graphData" :layout="graphLayout" :displayModeBar="true"/></b-card-text>
                                </b-col>
                            </b-row>
                        </b-tab>
                        
                        <b-tab title="Past Data" ><b-card-text>
                            <b-row class="pb-4">
                                <b-col cols="12">
                                    <h1 class="text-center">Past Data</h1>
                                </b-col>
                            </b-row>
                            <b-row>    
                                <b-col v-for="day in days" :key="day.id" class="text-center">
                                    <h5 class="font-weight-bold">{{ day.day }}</h5>
                                    <h5>{{ new Date(day.created).toLocaleDateString() }}</h5>
                                    <hr/>
                                    <img :src="`https://developer.accuweather.com/sites/default/files/${('0'+day.weather.weatherIcon).slice(-2)}-s.png`"/>
                                    <h6>{{ Math.round(day.weather.minTemperature) }}&deg;/ {{ Math.round(day.weather.maxTemperature) }} &deg;</h6>
                                    <hr/>
                                    <p>Income: <br/> ${{ day.income }} </p>
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col cols="12" class="pt-5">
                                    <b-pagination v-model="currentPage" :total-rows="totalRows" :per-page="7" align="center"></b-pagination>
                                </b-col>
                            </b-row>
                        </b-card-text></b-tab>

                        <b-tab title="Add Data"><b-card-text>
                            <b-row>
                                <b-col cols="6" offset="3">
                                    <b-form @submit.prevent="addData">
                                        <h1 class="text-center">Insert Daily Data</h1>
                                        <label>Total Sales ($)</label>
                                        <b-input type="text" v-model="income"></b-input>
                                        <br/>
                                        <label>Total Number of Sales</label>
                                        <b-input type="text" v-model="sales"></b-input>
                                        <br/>
                                        <b-button type="submit" variant="primary" block>Add Daily Data</b-button>
                                    </b-form>
                                </b-col>
                            </b-row>
                        </b-card-text></b-tab>

                        <b-tab title="Settings"><b-card-text>
                            <b-row>
                                <b-col cols="6" offset="3">
                                   <b-form>
                                        <h1 class="text-center">Account</h1>
                                        <label>Email</label>
                                        <b-input type="text"></b-input>
                                        <br/>
                                        <label>Password</label>
                                        <b-input type="password"></b-input>
                                        <br/>
                                        <label>Confirm Password</label>
                                        <b-input type="password"></b-input>
                                        <br/>
                                        <hr/>
                                        <h1 class="text-center">Business</h1>
                                        <label>Business Name</label>
                                        <b-input type="text"></b-input>
                                        <br/>
                                        <label>Category</label>
                                        <b-form-select :options="categories"></b-form-select>
                                        <br/>
                                        <br/>
                                        <b-button variant="primary" block>Apply Changes</b-button>
                                   </b-form> 
                                </b-col>
                            </b-row>    
                        </b-card-text></b-tab>
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
            income: '',
            sales: '',
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
                x: ["Janurary","February","March","April","May","June","July","August","September","October","November","December"],
                y: [15, 10, 33, 23, 52, 45, 76, 62, 81, 10, 52, 50],
                type:"scatter",
                name: "Historical Avgs"
            }],
            yearly2:{ // this year so far
                x: ["Janurary","February","March","April","May","June","July","August","September","October","November","December"],
                y: [30, 10, 20, 24, 53],
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
                },
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
                },
            },
            toggleText:'Weekly',
            dailyStats:['','',''],
            categories:[],
            reports: [],
            predictions: [],
            currentPage: 1
        };
    },
    methods: {
        toggle(toggle){
            this.toggleText = toggle;

        },
        scale(zoom) {
            setTimeout(() => {
                zoom.click();
            }, 1500);
        },
        addData(){
            axios.post('/api/reports/add/' + this.$route.params.id, {
                income: this.income,
                sales: this.sales
            }).then(reponse => {
                if(reponse.data === true){
                    alert('Data Added!');
                    this.income = '';
                    this.sales = '';
                }else{
                    throw new Error();
                }
            }).catch(error => {
                console.log(error);
            })
        }      
    },
    computed: {
        graphData(){
            let zoom = document.querySelector('a[data-title="Autoscale"]');
            if(zoom){
                this.scale(zoom);
            }
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
        totalRows() {
            if(!this.reports) return 0;
            return this.reports.length;
        },
        days() {
            return this.reports.slice((this.currentPage - 1) * 7, (this.currentPage - 1) * 7 + 7);
        }
    },
    mounted(){
        axios.get('/api/averages/week/' + this.$route.params.id)
        .then(response => {
            this.weekly[0].y = response.data;
        }).catch(error => {
            console.log(error)
        });

        axios.get('/api/averages/day/' + this.$route.params.id)
        .then((response)=>{
            this.dailyStats = response.data;
        })
        .catch(function (error) {
            console.log(error);
        });

        axios.get('/api/averages/year/' + this.$route.params.id)
        .then(response => {
            this.yearly[0].y = response.data;
        }).catch(error => {
            console.log(error);
        });

        axios.get('/api/predictions/week/' + this.$route.params.id)
        .then(response => {
            this.weekly[1].y = response.data;
        }).catch(error => {
            console.log(error);
        });

        axios.get('/api/reports/year/' + + this.$route.params.id)
        .then(response => {
            this.yearly[1].y = response.data;
        }).catch(error => {
            console.log(error);
        });

        axios.get('/api/reports/week/' + + this.$route.params.id)
        .then(response => {
            this.weekly[2].y = response.data;
        }).catch(error => {
            console.log(error);
        });

        axios.get('/api/reports/list/' + + this.$route.params.id)
        .then(response => {
            this.reports = response.data;
        }).catch(error => {
            console.log(error);
        });

        axios.get('/api/predictions/list/' + + this.$route.params.id)
        .then(response => {
            this.predictions = response.data;
        }).catch(error => {
            console.log(error);
        });

        axios.get('/api/categories/list')
            .then((response)=>{
                this.categories = response.data.map(category => {
                    return {
                        value: category.id,
                        text: category.name
                    };
                });
            })
            .catch(function (error){
                console.log(error);
            })
            .then(function () {

            });

        
        this.yearly.push(this.yearly2);
        this.weekly.push(this.weekly2);
        this.weekly.push(this.weekly3);
        feather.replace({
            'stroke-width':2
        });
    }    
}
</script>