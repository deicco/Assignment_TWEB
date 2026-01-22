/**
 * @file app.js
 * @description Main configuration for the Central Server (Port 3000).
 * Handles middleware, CORS for WebStorm compatibility, and API routing.
 * @version 1.4.0
 */

const express = require('express');
const path = require('path');
const cors = require('cors'); // FIXED: Added missing require statement
const moviesRouter = require('./routes/movie');
const app = express();

/** * Swagger documentation modules */
const swaggerUi = require('swagger-ui-express');
const swaggerSpec = require('./swagger');

/**
 * Global Middleware Configuration
 */
// Enable CORS to allow requests from WebStorm (Port 63343)
app.use(cors());

app.use(express.json());
app.use(express.urlencoded({ extended: false }));

/**
 * @description Serves static assets from the frontend directory.
 *
 */
app.use(express.static(path.join(__dirname, '../../frontend')));

/**
 * @description Swagger API documentation route.
 */
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

/**
 * @description Primary API routes for movie data.
 *
 */
app.use('/movies', moviesRouter);

/**
 * @description SPA Fallback Middleware.
 * Captures non-API requests and serves index.html to support frontend navigation.
 *
 */
app.use((req, res, next) => {
    if (req.path.startsWith('/movies') || req.path.startsWith('/api-docs') || req.path.includes('.')) {
        return next();
    }
    if (req.method === 'GET') {
        return res.sendFile(path.join(__dirname, '../../frontend/index.html'));
    }
    next();
});

/**
 * @description Global Error Handling Middleware.
 *
 */
app.use((error, req, res, next) => {
    console.error("Critical Server Error:", error.stack);
    res.status(error.status || 500).send(`Error: ${error.message}`);
});

module.exports = app;