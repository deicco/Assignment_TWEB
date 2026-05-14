/**
 * @file app.js
 * @description Main configuration for the Central Server (API Gateway) on Port 3000.
 * Configures middleware, Handlebars view engine for Server-Side Rendering (SSR), and routing.
 * @version 2.0.0
 */

const express = require('express');
const path = require('path');
const cors = require('cors');
const { engine } = require('express-handlebars');

const indexRouter = require('./routes/index');

const swaggerUi = require('swagger-ui-express');
const swaggerSpec = require('./swagger');

const app = express();

/**
 * View Engine Configuration.
 * Sets up Handlebars as the default template engine for Server-Side Rendering.
 * Also registers global helpers for data formatting within templates.
 */
app.engine('hbs', engine({
    extname: '.hbs',
    defaultLayout: 'layout',
    layoutsDir: path.join(__dirname, 'views/layouts'),
    partialsDir: path.join(__dirname, 'views/partials'),
    helpers: {
        /**
         * Helper to format the release year.
         */
        getYear: (y) => (y && y > 1850) ? y : 'N/A',

        /**
         * Helper to format the rating.
         */
        formatRating: (r) => (r && r > 0) ? r : 'N/A',

        // --- NEW PAGINATION HELPERS ---

        /** If a == b, ret true */
        eq: (a, b) => a === b,

        /** Math sum */
        add: (a, b) => Number(a) + Number(b),

        /** Math sub */
        sub: (a, b) => Number(a) - Number(b)
    }
}));
app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));
/**
 * Global Middleware Configuration.
 */
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

/**
 * Static assets configuration.
 * Serves CSS, JS, and images from the public directory.
 */
app.use(express.static(path.join(__dirname, 'public')));

/**
 * Swagger API documentation route.
 */
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

/**
 * Primary application routes for SSR.
 */
app.use('/', indexRouter);

/**
 * Global Error Handling Middleware.
 * Captures and logs exceptions, preventing the server from crashing.
 */
app.use((error, req, res, next) => {
    console.error("[Gateway Error] Critical Server Exception:", error.stack);
    res.status(error.status || 500).send(`Error: ${error.message}`);
});

module.exports = app;