<template>
    <b-row id="mainBody" class="bg-secondary">
        <b-col cols="12" md="4" offset-md="4" id="login">
            <div class="my-5 p-5 rounded bg-white">
                <h1 class="text-center">Login</h1>
                <hr/>
                <label for="username" class="py-2" >Username:</label>
                <b-form-input id="username" size="lg" type="text" placeholder="Username" class="mb-4"></b-form-input>
                <label for="password" class="py-2">Password:</label>
                <b-form-input id="password" size="lg" type="password" placeholder="Password" class="mb-4"></b-form-input>
                <b-button block size="lg" variant="primary">Login</b-button>
            </div>
        </b-col>
    </b-row>
</template>

<style>
    #login h1{
        font-size:38pt;
    }
    #login label{
        font-size:16pt;
    }
</style>