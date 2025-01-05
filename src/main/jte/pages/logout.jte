@param String username
@param org.springframework.security.web.csrf.CsrfToken csrfToken

@template.layouts.mtLayout(title = "index", content = @`
    <section class="grid text-center h-screen items-center p-8">
        <div>
            <h3
                    class="block antialiased tracking-normal font-sans text-3xl font-semibold leading-snug text-blue-gray-900 mb-2"
            >
                Logging out user - ${username}
            </h3>
            <p
                    class="block antialiased font-sans text-base font-light leading-relaxed text-inherit mb-16 text-gray-600 font-normal text-[18px]"
            >
                Please wait while we redirected you to login.
            </p>
            <div class="grid min-h-[140px] w-full place-items-center overflow-x-scroll rounded-lg p-6 lg:overflow-visible">
                <div class="flex items-end gap-8 mx-auto max-w-[48rem] text-left">
                    <form method="post" action="/logout" name="logout">
                        <span class="loading loading-infinity loading-lg text-info"></span>
                        <input type="hidden"
                               name="${csrfToken.getParameterName()}"
                               value="${csrfToken.getToken()}"/>
                    </form>
                </div>
            </div>
        </div>
        <footer class="w-full bg-white p-8">
            <p class="block mb-4 text-sm text-center text-slate-500 md:mb-0 border-t border-slate-200 mt-4 pt-4">
                Copyright © 2024&nbsp;
                <a href="https://material-tailwind.com/" target="_blank" rel="noreferrer">01101011</a>.
            </p>
        </footer>
        <script type="text/javascript">
            window.onload = function(){
                setTimeout(() => {
                    document.forms['logout'].submit();
                }, 1000);
            }
        </script>
    </section>
`)