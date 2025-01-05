@template.layouts.mtLayout(title = "Token Based Login", content = @`
    <section class="grid text-center h-screen items-center p-8">
        <div>
            <p
                    class="block antialiased font-sans text-base font-light leading-relaxed text-inherit mb-10 text-gray-600 font-normal text-[18px]"
            >
                We have received your request.
            </p>
            <h3
                    class="block antialiased tracking-normal font-sans text-3xl font-semibold leading-snug text-blue-gray-900 mb-2"
            >
                Email Link is on the way!
            </h3>
            <p
                    class="block antialiased font-sans text-base font-light leading-relaxed text-inherit mb-4 text-gray-600 font-normal text-[18px]"
            >
                (Depends on the fact - if we have found you in our records)
            </p>
            <p
                    class="block antialiased font-sans text-base font-light leading-relaxed text-inherit mb-4 text-gray-600 font-normal text-[18px]"
            >
                You will be redirected to home upon clicking it.
            </p>
            <div class="grid min-h-[140px] w-full place-items-center overflow-x-scroll rounded-lg p-6 lg:overflow-visible">
                <div class="flex items-end gap-8 mx-auto max-w-[48rem] text-left">
                    <span>
                        <img src="/imgs/mailbox.gif" alt="mail sent" style="width: 253px;"/>
                    </span>
                </div>
            </div>
        </div>
        <footer class="footer text-content p-5 footer-center bottom-0 p-4">
            <aside>
                <p>Copyright © - 01101011</p>
            </aside>
        </footer>
    </section>
`)