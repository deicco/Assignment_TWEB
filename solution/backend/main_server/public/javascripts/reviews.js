/**
 * @file reviews.js
 * @description Client-side script handling asynchronous review pagination using Axios.
 * Implements an event-driven, non-blocking flow that preserves the state of real-time components (Socket.io).
 */

document.addEventListener("DOMContentLoaded", () => {
    const wrapper = document.getElementById('reviews-wrapper');
    if (!wrapper) return;

    const movieName = wrapper.getAttribute('data-movie-name');
    const reviewsList = document.getElementById('reviews-list');
    const prevBtn = document.getElementById('prev-rev-btn');
    const nextBtn = document.getElementById('next-rev-btn');
    const prevItem = document.getElementById('prev-page-item');
    const nextItem = document.getElementById('next-page-item');
    const pageIndicator = document.getElementById('page-indicator');

    /**
     * Dispatches an asynchronous Ajax request using Axios to fetch next review block.
     * @param {number} page - Target review page index.
     */
    async function fetchReviews(page) {
        try {
            // Non-blocking asynchronous communication (Ref: Lecture 11.b, Lecture 11.c)
            const response = await axios.get(`/api/reviews?movieName=${encodeURIComponent(movieName)}&page=${page}`);
            const data = response.data;

            // 1. Clear the list and rebuild elements dynamically
            reviewsList.innerHTML = '';
            if (data.reviews && data.reviews.length > 0) {
                data.reviews.forEach(review => {
                    // Default styling classes for standard reviews
                    let cardClass = 'p-3 rounded mb-3 bg-dark border border-secondary text-light';
                    let badgeClass = 'bg-secondary';

                    // Conditional evaluation based on Rotten Tomatoes specification (Ref: Assignment Req 3)
                    if (review.review_type === 'Fresh') {
                        cardClass = 'p-3 rounded mb-3 alert alert-success';
                        badgeClass = 'bg-success';
                    } else if (review.review_type === 'Rotten') {
                        cardClass = 'p-3 rounded mb-3 alert alert-danger';
                        badgeClass = 'bg-danger';
                    }

                    const typeBadge = review.review_type ? ` - <span class="badge ${badgeClass}">${review.review_type}</span>` : '';
                    const scoreText = review.review_score ? `<p class="mb-0"><small class="text-orange">Voto: ${review.review_score}</small></p>` : '';

                    reviewsList.innerHTML += `
                        <li class="${cardClass}">
                            <strong>${review.critic_name}</strong>${typeBadge}
                            <p class="mt-2 mb-1">${review.review_content}</p>
                            ${scoreText}
                        </li>
                    `;
                });
            } else {
                reviewsList.innerHTML = '<li>Nessuna recensione disponibile per questo film.</li>';
            }

            // 2. Synchronize visual indicators and page counter
            pageIndicator.textContent = `Pagina ${data.current + 1} di ${data.total}`;

            // 3. Manage navigation operational state flags
            if (data.hasPrevPage) {
                prevItem.classList.remove('disabled');
                prevBtn.setAttribute('data-page', data.current - 1);
            } else {
                prevItem.classList.add('disabled');
                prevBtn.setAttribute('data-page', '-1');
            }

            if (data.hasNextPage) {
                nextItem.classList.remove('disabled');
                nextBtn.setAttribute('data-page', data.current + 1);
            } else {
                nextItem.classList.add('disabled');
                nextBtn.setAttribute('data-page', '-1');
            }

        } catch (error) {
            console.error("[Ajax Error] Failed to update reviews asynchronously:", error);
        }
    }

    /**
     * Intercepts the classical link click event to prevent full-page reload.
     */
    function handlePaginationClick(e) {
        e.preventDefault();
        const targetPage = parseInt(e.currentTarget.getAttribute('data-page'));
        if (targetPage >= 0) {
            fetchReviews(targetPage);
        }
    }

    if (prevBtn) prevBtn.addEventListener('click', handlePaginationClick);
    if (nextBtn) nextBtn.addEventListener('click', handlePaginationClick);
});