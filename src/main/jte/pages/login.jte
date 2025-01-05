@param String username
@param org.springframework.security.web.csrf.CsrfToken csrfToken
@param String csrfHeader
@param String contextPath

@template.layouts.mtLayout(title = "index", content = @`
    <section class="grid text-center h-screen items-center p-8">
        <div>
            <h3 class="block antialiased tracking-normal font-sans text-3xl font-semibold leading-snug text-blue-gray-900 mb-2">
                Sign In
            </h3>
            <p class="block antialiased font-sans text-base font-light leading-relaxed text-inherit mb-8 text-gray-600 font-normal text-[18px]">
                Enter your username(or email) and password to sign in
            </p>
            <form class="mx-auto max-w-[24rem] text-left" method="post" action="/login">
                <div class="mb-6">
                    <label for="username" class="block mb-2 text-sm text-slate-600">
                        Enter username
                    </label>
                    <input id="user" name="username" placeholder="Username or Email" type="text" class="w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md px-3 py-2 transition duration-300 ease focus:outline-none focus:border-blue-500 hover:border-blue-300 shadow-sm focus:shadow" />
                </div>
                <div class="mb-6">
                    <label for="password" class="block mb-2 text-sm text-slate-600">
                        Enter password
                    </label>
                    <input id="password" name="password" placeholder="Password" type="password" class="w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md px-3 py-2 transition duration-300 ease focus:outline-none focus:border-blue-500 hover:border-blue-300 shadow-sm focus:shadow" />
                </div>
                <div class="mb-6">
                    <input type="hidden"
                           name="${csrfToken.getParameterName()}"
                           value="${csrfToken.getToken()}"/>
                    <input type="hidden" name="contextPath" value="${contextPath}"/>
                    <input type="hidden" name="csrfHeaders" value="${csrfHeader}"/>
                    <button
                            class="align-middle select-none font-sans font-bold text-center uppercase transition-all disabled:opacity-50 disabled:shadow-none disabled:pointer-events-none text-sm py-3.5 px-7 rounded-lg bg-gray-900 text-white shadow-md shadow-gray-900/10 hover:shadow-lg hover:shadow-gray-900/20 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none bg-gray-900 block w-full gap-3 mt-6"
                            type="submit"
                            data-ripple-light="true">
                        sign in
                    </button>
                </div>
            </form>
            <div class="divider mx-auto max-w-[24rem]">OR</div>
            <form class="mx-auto max-w-[24rem] text-left" method="post" action="/magic/link/generate">
                <div class="mb-6">
                    <label for="username" class="block mb-2 text-sm text-slate-600">
                        Enter email or username
                    </label>
                    <input id="username-magic-link" name="username" class="w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md px-3 py-2 transition duration-300 ease focus:outline-none focus:border-blue-500 hover:border-blue-300 shadow-sm focus:shadow" placeholder="Username" type="text" />
                </div>
                <div class="mb-6">
                    <input type="hidden"
                           name="${csrfToken.getParameterName()}"
                           value="${csrfToken.getToken()}"/>
                    <input type="hidden" name="contextPath" value="${contextPath}"/>
                    <input type="hidden" name="csrfHeaders" value="${csrfHeader}"/>
                    <button
                            class="align-middle select-none font-sans font-bold text-center uppercase transition-all disabled:opacity-50 disabled:shadow-none disabled:pointer-events-none text-sm py-3.5 px-7 rounded-lg bg-gray-900 text-white shadow-md shadow-gray-900/10 hover:shadow-lg hover:shadow-gray-900/20 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none bg-gray-900 block w-full gap-3 mt-6"
                            type="submit"
                            data-ripple-light="true">
                        sign in via Magic Link
                    </button>
                </div>
            </form>
        </div>
        <footer class="w-full bg-white p-8">
            <div class="divider mx-auto max-w-[24rem]">Copyright © 2024 01101011.</div>
        </footer>
        <script type="text/javascript">
            let csrfHeader = JSON.parse("${csrfHeader}");
            document.addEventListener("DOMContentLoaded",() => setupLogin(csrfHeader, "${contextPath}", document.getElementById('passkey-signin')));
        </script>
    </section>
`)