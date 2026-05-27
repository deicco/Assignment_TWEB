/**
 * @file app.js
 * @description Main application configuration file for the Express Gateway Server.
 * Configures Handlebars view engine, global middlewares, CORS policies,
 * Swagger UI documentation routing, and mounts the core API routers.
 * @version 1.1.0
 */

const express = require('express');
const path = require('path');
const cors = require('cors');
const { engine } = require('express-handlebars');

// Import Swagger UI and specification dependencies (Ref: Lecture 13 Swagger)
const swaggerUi = require('swagger-ui-express');
const swaggerSpec = require('./Swagger');

console.log("DEBUG SWAGGER SPEC:", JSON.stringify(swaggerSpec.paths, null, 2));

// Import core application route controllers
const indexRouter = require('./routes/index');

const app = express();

/**
 * Handlebars Template Engine Configuration
 * Registers directory layouts, partials, and standard presentation helper utilities.
 */
app.engine('hbs', engine({
    extname: '.hbs',
    defaultLayout: 'layout',
    layoutsDir: path.join(__dirname, 'views/layouts'),
    partialsDir: path.join(__dirname, 'views/partials'),
    helpers: {
        /**
         * Extracts and safe-guards movie production release year metrics.
         */
        getYear: (y) => {
            if (!y) return 'N/A';
            return y;
        },
        /**
         * Formats raw numeric evaluations into standardized single decimal ratings.
         */
        formatRating: (r) => {
            if (!r) return '0.0';
            return parseFloat(r).toFixed(1);
        },
        /**
         * Strict structural equality evaluator helper.
         */
        eq: (a, b) => a === b,
        /**
         * Basic arithmetic addition helper utility for pagination indexing.
         */
        add: (a, b) => Number(a) + Number(b),
        /**
         * Basic arithmetic subtraction helper utility for pagination indexing.
         */
        sub: (a, b) => Number(a) - Number(b)
    }
}));

app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

// Global Middlewares initialization (Ref: Lecture 11.e CORS)
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));

/**
 * Swagger UI Documentation Endpoint Mounting (Ref: Lecture 13 Swagger)
 * Serves the compiled specification UI for interactive testing.
 */
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

/**
 * Core Business Logic Routing Middleware
 */
app.use('/', indexRouter);

module.exports = app;