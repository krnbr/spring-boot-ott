@param String username
@param org.springframework.security.web.csrf.CsrfToken csrfToken

@template.layouts.commonLayout(username = username, title = "Profile", content = @`
    <div>
        <h1 class="text-2xl font-bold mb-4">Welcome, ${username}!</h1>
        <p class="text-gray-600">This is your dashboard. Here you can view your recent activity and manage your account.</p>
    </div>
`, csrfToken = csrfToken)