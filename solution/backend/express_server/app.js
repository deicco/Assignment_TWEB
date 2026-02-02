/**
 * Main Express application setup.
 * Configures middleware, routes, and database connection.
 */

var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
const database = require('./databases/movieverse')
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var reviewsRouter = require('./routes/reviews');
var moviesRouter = require('./routes/movies');

//Swagger
const swaggerUi = require('swagger-ui-express');
const swaggerSpec = require('./swagger');

var app = express();

/**
 * Application middleware configuration.
 */
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
//Swagger route
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

/**
 * Application route definitions.
 */
app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/reviews', reviewsRouter);
app.use('/movies', moviesRouter);

module.exports = app;
