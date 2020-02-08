<template>
    <div>
         <!-- HEADER -->
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark px-5 py-3">
            <b-link class="navbar-brand navbar-font" to="/">FinTech</b-link>
            <div class="navbar-nav ml-auto">
                <b-link class="nav-link btn rounded-full bg-primary active px-5 navbar-font font-weight-bold" to="/login">Login</b-link>
            </div>  
        </nav>
        <router-view/>
    </div>
</template>