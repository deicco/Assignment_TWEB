/**
 * Main Express application setup.
 * Configures middleware, routes, and database connection for the Dataset Microservice.
 */

const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');

// Inizializza la connessione a MongoDB e l'eventuale importazione dei CSV
require('./databases/movieverse');

const reviewsRouter = require('./routes/reviews');
const moviesRouter = require('./routes/movies');

// Swagger Configuration
const swaggerUi = require('swagger-ui-express');
const swaggerSpec = require('./swagger');

const app = express();

/**
 * Application middleware configuration.
 */
app.use(logger('dev'));
app.use(express.json()); // Fondamentale per leggere req.body nelle POST
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

/**
 * Swagger Route
 */
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

/**
 * Application route definitions.
 * Solo Movies e Reviews, niente Index o Users!
 */
app.use('/reviews', reviewsRouter);
app.use('/movies', moviesRouter);

module.exports = app;