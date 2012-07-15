task :default => :test

desc "Clean generated files"
task :clean  do
  rm_rf 'public/js/gen'
  rm_rf "public/css/gen"
  rm_rf "target/classes"
end

desc 'Compile less, compress generated css'
task :css_compile do
	#ToDo
end

desc "Prepare for development"
task :prepare => [:css_compile]

desc "Javac"
task :javac do
  sh 'lein javac'
end

desc "Run lein unit test"
task :test => [:clean, :prepare, :javac] do
  sh 'lein test'
end

desc "lein swank"
task :swank => [:clean, :prepare, :javac] do
  sh "lein swank"
end

namespace :run do
  desc "Run server in dev profile"
  task :dev => [:prepare, :javac] do
    sh 'lein run'
  end
end

# namespace :watch do
#   desc 'Watch css, html'
#   task :all => [:css_compile] do
#     # sh "echo -n \"\033]0;rssminer rake watch\007\""
#     t1 = Thread.new do
#       sh 'while inotifywait -r -e modify public/bootstrap/less; do rake css_compile; done'
#     end
#     trap(:INT) {
#       sh "killall inotifywait"
#     }
#     t1.join
#   end
# end