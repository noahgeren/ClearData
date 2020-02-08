<template>
    <div>
         <!-- HEADER -->
        <nav class="navbar navbar-dark fixed-top bg-dark px-4 py-3">
            <b-link class="navbar-brand navbar-font" to="/">FinTech</b-link>
            <div class="navbar-nav ml-auto">
                <b-link class="nav-link btn rounded-full bg-primary active px-5 navbar-font font-weight-bold" to="/login">Login</b-link>
            </div>  
        </nav>
        <router-view/>
        <!-- FOOTER -->
        <b-row class="text-center bg-dark text-light py-4" id="footer">
            <b-col>
                <h5>&copy;2020 FinTech - All Rights Reserved</h5>
            </b-col>
        </b-row>
    </div>
</template>