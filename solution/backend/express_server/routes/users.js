/**
 * Routes for handling user-related API endpoints.
 */

var express = require('express');
var router = express.Router();

/**
 * @swagger
 * /users:
 *   get:
 *     summary: Get users placeholder
 *     description: Returns a placeholder response for the users route
 *     responses:
 *       200:
 *         description: Successful response with placeholder text
 *         content:
 *           text/plain:
 *             schema:
 *               type: string
 *               example: respond with a resource
 */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

module.exports = router;
