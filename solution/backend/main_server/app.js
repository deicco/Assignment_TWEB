const express = require('express');
const path = require('path');
const cors = require('cors');
const { engine } = require('express-handlebars');
const indexRouter = require('./routes/index');

const app = express();

app.engine('hbs', engine({
    extname: '.hbs',
    defaultLayout: 'layout',
    layoutsDir: path.join(__dirname, 'views/layouts'),
    partialsDir: path.join(__dirname, 'views/partials'),
    helpers: {
        getYear: (y) => {
            if (!y) return 'N/A';
            return y; // In Movies.java 'date' è già un Integer (anno)
        },
        formatRating: (r) => {
            if (!r) return '0.0';
            return parseFloat(r).toFixed(1);
        },
        eq: (a, b) => a === b,
        add: (a, b) => Number(a) + Number(b),
        sub: (a, b) => Number(a) - Number(b)
    }
}));

app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);

module.exports = app;