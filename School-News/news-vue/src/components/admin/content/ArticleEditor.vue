<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard'}">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/content/book'}">新闻管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/content/article'}">文章管理</el-breadcrumb-item>
        <el-breadcrumb-item>编辑器</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
<!--    {{this.$route.params.article}}-->
    <el-form v-model="article" style="text-align: left" ref="dataForm">
      <el-form-item label="作者" prop="author">
        <el-input v-model="article.articleAuthor" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="发布日期" prop="date">
<!--        <el-input v-model="article.articleDate" autocomplete="off"></el-input>-->
        <el-date-picker
          type="datetime"
          v-model="article.articleDate"
          placeholder="选择开始时间"
          size="mini"
          style="width: 250px;"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="startPickerOptions">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="新闻等级" prop="cover">
        <el-input v-model="article.articleRate" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="审核状态" prop="cover">
        <el-input v-model="article.audit_status.status" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="新闻栏目" prop="category">

          <el-input v-model="article.category.name" autocomplete="off" :disabled="true"></el-input>
        <el-form-item label="栏目修改" prop="category">
        <el-select v-model="form.category" placeholder="请选择">
          <el-option
            v-for="item in category"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        </el-form-item>
      </el-form-item>
      <el-form-item label="视频上传" prop="Video">
        <!-- action必选参数, 上传的地址 -->
        <el-upload class="avatar-uploader el-upload--text" action="http://localhost:8443/api/admin/content/books/covers"
                   :show-file-list="false"
                   with-credentials
                   :on-success="handleVideoSuccess"
                   :before-upload="beforeUploadVideo" :on-progress="uploadVideoProcess">
          <video v-if="article.Video !='' && videoFlag == false" :src="article.Video" class="avatar" controls="controls">您的浏览器不支持视频播放</video>
          <i v-else-if="article.Video =='' && videoFlag == false" class="el-icon-plus avatar-uploader-icon"></i>
          <el-progress v-if="videoFlag == true" type="circle" :percentage="videoUploadPercent" style="margin-top:30px;"></el-progress>
        </el-upload>
        <P class="text">请保证视频格式正确，且不超过30M</P>
      </el-form-item>
      <el-form-item label="审核意见" prop="desc">
        <el-input type="textarea" v-model="article.articleAuditMind" autosize :disabled="true"></el-input>
      </el-form-item>
      <el-form-item prop="id" style="height: 0">
        <el-input type="hidden" v-model="article.id" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
<!--    <Upload action="" :before-upload="handleBeforeUpload" accept=".doc, .docx">-->
<!--      <Button  :loading="uploadLoading">上传文件</Button>-->
<!--    </Upload>-->
    <el-row>
      <el-input
        v-model="article.articleTitle"
        style="margin: 10px 0px;font-size: 18px;"
        placeholder="请输入标题"></el-input>
    </el-row>
    <el-row style="height: calc(100vh - 140px);">
      {{'提示：ctrl+s可保存，或点击“添加摘要/封面”左边的按钮'}}
      <mavon-editor
        v-model="article.articleContentMd"
        style="height: 100%;"
        ref=md
        @save="saveArticles"
        fontSize="16px">

        <el-button slot="left-toolbar-after" @click="dialogVisible = true">添加摘要/封面</el-button>
<!--        <el-button slot="right-toolbar-before" >保存</el-button>-->

        <!--        <button type="button"  :title="'摘要/封面'" slot="left-toolbar-after"-->
<!--                @click="dialogVisible = true"></button>-->

      </mavon-editor>

      <el-dialog
        :visible.sync="dialogVisible"
        width="30%">
        <el-divider content-position="left">摘要</el-divider>
        <el-input
          type="textarea"
          v-model="article.articleAbstract"
          rows="6"
          maxlength="255"
          show-word-limit></el-input>
        <el-divider content-position="left">封面</el-divider>
        <div style="margin-top: 20px">
          <el-input v-model="article.articleCover" autocomplete="off" placeholder="1图片 URL"></el-input>
          <img-upload @onUpload="uploadImg" ref="imgUpload" style="margin-top: 5px"></img-upload>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
    </el-row>
  </div>

</template>

<script>
  import ImgUpload from './ImgUpload'
  import Cookie from "js-cookie";
  export default {
    name: 'Editor',
    components: {
      ImgUpload
    },
    computed:{
      startPickerOptions(){
        //选择的日期
        // moment.isMoment()
        let curDate = this.$moment(this.startDate).format("YYYY-MM-DD");
        //最小日期
        let minDate = this.$moment(this.minTime).format("YYYY-MM-DD");
        //前推一天，防止当天日期无法选择
        let preTime = this.minTime-24*3600000;
        // 如果选择的日期为最小日期，则设置为最小日期的时分秒
        let str = "";
        if(curDate == minDate){
          str = this.$moment(this.minTime).format("HH:mm:ss");
        }else{
          str = "00:00:00"
        }
        return {
          disabledDate: (time) => {
            return time.getTime() < preTime || time.getTime() > this.maxTime;
          },
          selectableRange: str + " - 23:59:59"
        }
      },

    },
    data () {
      return {
        videoFlag: false,
        article: {
          audit_status: {},
          category: {}
        },
        dialogVisible: false,
        category: [],
        form: {
          category: ''
        }
      }
    },
    created() {
      this.getChoiceList();
    },
    mounted () {

      if (this.$route.params.article) {
        this.article = this.$route.params.article
        console.log("category:"+this.article.category.id)
      }else {
        console.log("???????????")
      }
    },
    methods: {
      handleVideoSuccess(res, file) {
        console.log('upload video = ', res)
        //获取上传图片地址
        this.videoFlag = false;
        this.videoUploadPercent = 0;
        if(res){
          // this.videoForm.videoUploadId = res.data.uploadId;
          this.article.Video = res;
        }else{
          this.$message.error('视频上传失败，请重新上传！');
        }
      },
      uploadVideoProcess (event, file, fileList) {
        console.log(event.percent, file, fileList)
        this.videoFlag = true
        // this.videoUploadPercent = file.percentage.toFixed(0)
        // this.videoUploadPercent = event.percent.toFixed(0)
        this.videoUploadPercent = Math.floor(event.percent)
      },
      beforeUploadVideo(file) {
        const isLt10M = file.size / 1024 / 1024  < 30;
        if (['video/mp4', 'video/ogg', 'video/flv','video/avi','video/wmv','video/rmvb'].indexOf(file.type) == -1) {
          this.$message.error('请上传正确的视频格式');
          return false;
        }
        if (!isLt10M) {
          this.$message.error('上传视频大小不能超过30MB哦!');
          return false;
        }
      },
      readFile (file) {

        const reader = new FileReader()

        reader.readAsArrayBuffer(file)

        reader.onload = e => {

          const data = reader.result

          mammoth.extractRawText({arrayBuffer: data}).then(r=>{

            console.log(r.value)

          })

        }

      },
      getChoiceList(){
        this.$axios.get('/categorys').then(resp => {
          console.log('getChoiceList',resp)
          // console.log(resp.data.result.list)
          if (resp && resp.data.code === 200) {
            this.category = resp.data.result
            // resp.data.result.list.forEach(element => {
            //   this.category.push({id:element.id,name:element.name});
            // })
          }
        }).catch(function (error) {
          console.log(error)
        })
      },
      saveArticles (value, render) {
        // value 是 md，render 是 html
        this.$confirm('是否保存文章?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var categoryId,categoryName,article_title,categoryid,categoryoject;
          categoryid = this.form.category
          categoryoject = this.category.find(item => item.id == categoryid)
          article_title=this.article.articleTitle
          var username =Cookie.get('username')
          if(categoryid=='')
          {
            categoryid=this.article.category.id
            categoryoject = this.category.find(item => item.id == categoryid)
            console.log("??")
            // category_change=0;
          }
          // console.log("categoryid:"+categoryid)
          // console.log("categoryname:"+categoryoject.name)
          // categoryId=categoryid;
          // categoryName=categoryname;

          if(article_title==null)
          {
            this.$message.error('新闻标题不能为空');
            return false;
          }
            this.$axios
              .post('/admin/content/article'+'/'+username, {
                id: this.article.id,
                articleTitle: article_title,
                articleAuthor: this.article.articleAuthor,
                articleRate: this.article.articleRate,
                audit_status:
                  {
                    id:this.article.audit_status.id,
                    name:this.article.audit_status.status
                  },
                articleContentMd: value,
                articleContentHtml: render,
                articleAbstract: this.article.articleAbstract,
                articleCover: this.article.articleCover,
                articleDate: this.article.articleDate,
                category: categoryoject,
                articleVideo:this.article.Video,
                articleAuditMind:this.article.articleAuditMind

              }).then(resp => {
              if (resp && resp.data.code === 200) {
                this.$message({
                  type: 'info',
                  message: '已保存成功'
                })
              }else {
                this.$message.error('保存失败，请检查填写是否完整或有无其他问题')
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
      },
      uploadImg (url) {
        console.log('编辑的url = ', url)
        this.article.articleCover = url
      }
    }
  }
</script>
