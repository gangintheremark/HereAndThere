<template>
    <footer class="footer">
        <div class="footer-content">
            <p>&copy; 2024 LEE & NAM Corp.</p>
            <nav>
                <ul>
                    <li><a href="/">홈</a></li>
                    <li><a href="/about">소개</a></li>
                    <li><a href="/board">Q&A</a></li>
                </ul>
            </nav>
        </div>
    </footer>
</template>

<script>
</script>

<style scoped>
.footer {
    padding: 20px;
    text-align: center;
}

.footer-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.footer nav ul {
    list-style: none;
    padding: 0;
}

.footer nav ul li {
    display: inline;
    margin-right: 10px;
}

.footer nav ul li a {
    text-decoration: none;
    color: #333;
}
</style>