/**
 * Home page route for the Express server.
 */

var express = require('express');
var router = express.Router();

/**
 * @swagger
 * /:
 *   get:
 *     summary: Server homepage
 *     description: Renders the main landing page of the Express server
 *     responses:
 *       200:
 *         description: Successfully rendered the homepage
 *         content:
 *           text/html:
 *             schema:
 *               type: string
 */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
